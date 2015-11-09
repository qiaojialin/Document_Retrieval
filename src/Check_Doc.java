import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import javax.xml.namespace.QName;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;



public class Check_Doc implements Runnable {

	private File file;
	
	private int type;
	private String text;
	
	public Check_Doc(File f, int t)
	{
		file = f;
		type = t;
		text = "";
	}
	
	public void run() 
	{

		
		if(type == 1)
			read2003();
		else 
			read2007();

		System.out.println(file.getAbsolutePath());
		System.out.println(text);
	} 

	//2003 正文+文本框+表格
    public void read2003()
    {
	    try {
	    	InputStream is = new FileInputStream(file);
		   	 WordExtractor ex;
		   	 ex = new WordExtractor(is);
		   	 text = ex.getText().trim();
	   		 } catch (Exception e) {
	   			// TODO Auto-generated catch block
	   			 e.printStackTrace();
	   		 }
    } 
   
    //2007 正文+文本框+表格
    private void read2007() {
		try 
		{
			//正文+表格
			OPCPackage opcPackage = POIXMLDocument.openPackage(file.getAbsolutePath());
			POIXMLTextExtractor extractor;
			extractor = new XWPFWordExtractor(opcPackage);
			text += extractor.getText();		
			//文本框
			InputStream inputStream = new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(inputStream);
			for (XWPFParagraph paragraph : document.getParagraphs())
			{
				XmlObject[] textBoxObjects =  paragraph.getCTP().selectPath(
						"declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' " 
	    		+ "declare namespace wps='http://schemas.microsoft.com/office/word/2010/wordprocessingShape' " +
	    				".//*/wps:txbx/w:txbxContent");
				for (int i =0; i < textBoxObjects.length; i++) 
				{
					XmlObject[] paraObjects = textBoxObjects[i].selectChildren(new QName(
							"http://schemas.openxmlformats.org/wordprocessingml/2006/main", "p"));
					for (int j=0; j<paraObjects.length; j++) 
					{
						XWPFParagraph embeddedPara = new XWPFParagraph(
								CTP.Factory.parse(paraObjects[j].xmlText()),
								paragraph.getBody()
								);
						text += embeddedPara.getText();
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}           
    }
    

	
	//获得文件大小
	public static double getSize(File f)
	{
		//得到指定目录下 文件的大小
		long s=0;
		if(f.exists())
		{
			try
			{
				FileInputStream fis=null;
				fis=new FileInputStream(f);
				s=fis.available();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		 
		DecimalFormat df=new DecimalFormat("#.00");
		String fileSizeString="";
		if(s<1024)
		{
			fileSizeString=df.format((double)s)+"B";
		}
		else if(s<1048576)
		{
			fileSizeString=df.format((double)s/1024)+"K";
		}
		else if(s<1073741824)
		{
			fileSizeString=df.format((double)s/1048576)+"M";
		}
		else 
		{
			fileSizeString=df.format((double)s/1073741824)+"G";
		}
	//	return fileSizeString;
		return s;
	}
	 
}




