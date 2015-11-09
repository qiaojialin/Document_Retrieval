import java.io.File;


public class Document_Retrieval 
{	
	public static void main(String[] args) 
	{
		//��ȡ������
		 File[] disks =File.listRoots();
		 //�����������ļ�
	      
         for(File a : disks)
        	 searchAllFile(a); 
         //File a = new File("e:\\test");   
         //searchAllFile(a);
	 }

	 
	 //�����ļ���f�µ������ļ�
	 public static void searchAllFile(File f)
	 {
         if(f.isDirectory())  //�ļ���
         {
        	 //��ø��ļ������������ļ������ļ���
             File[] f1 = f.listFiles();
             if(f1 == null)
            	 return;
             int length = f1.length;
             for(int i = 0;i < length;i++)
             {
            	 //�ݹ���ã�����ÿ���ļ�����
                 searchAllFile(f1[i]);
             }
         }
         else if(f.isFile())  //�ļ�
         {
        	 String extensionName;
        	 
        	 //����ļ���չ��
        	 extensionName = getExtensionName(f.getName());
        	 
        	 //�ж���չ���Ƿ�����Ҫ��
        	 if(extensionName.toLowerCase().equals("doc")) 
        		 new Thread(new Check_Doc(f, 1)).start();   //************************
        	 else if(extensionName.toLowerCase().equals("docx"))
        		 new Thread(new Check_Doc(f, 2)).start();
         }
	 }
     
	 
	 //����ļ���չ��
	 public static String getExtensionName(String filename) 
	 {
		 //����ļ����ڲ��Ҵ����ļ���
	     if ((filename != null) && (filename.length() > 0)) 
	     { 
	    	 //���.������
	    	 int dot = filename.lastIndexOf('.'); 
	         if ((dot >-1) && (dot < (filename.length() - 1))) 
	         {
	        	 return filename.substring(dot + 1); 
	         } 
	     } 
	     return filename; 
	 } 
	 
}
