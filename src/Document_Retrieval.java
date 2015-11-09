import java.io.File;


public class Document_Retrieval 
{	
	public static void main(String[] args) 
	{
		//获取所有盘
		 File[] disks =File.listRoots();
		 //遍历所有盘文件
	      
         for(File a : disks)
        	 searchAllFile(a); 
         //File a = new File("e:\\test");   
         //searchAllFile(a);
	 }

	 
	 //搜索文件夹f下的所有文件
	 public static void searchAllFile(File f)
	 {
         if(f.isDirectory())  //文件夹
         {
        	 //获得该文件夹下所有子文件和子文件夹
             File[] f1 = f.listFiles();
             if(f1 == null)
            	 return;
             int length = f1.length;
             for(int i = 0;i < length;i++)
             {
            	 //递归调用，处理每个文件对象
                 searchAllFile(f1[i]);
             }
         }
         else if(f.isFile())  //文件
         {
        	 String extensionName;
        	 
        	 //获得文件扩展名
        	 extensionName = getExtensionName(f.getName());
        	 
        	 //判断扩展名是否满足要求
        	 if(extensionName.toLowerCase().equals("doc")) 
        		 new Thread(new Check_Doc(f, 1)).start();   //************************
        	 else if(extensionName.toLowerCase().equals("docx"))
        		 new Thread(new Check_Doc(f, 2)).start();
         }
	 }
     
	 
	 //获得文件扩展名
	 public static String getExtensionName(String filename) 
	 {
		 //如果文件存在并且存在文件名
	     if ((filename != null) && (filename.length() > 0)) 
	     { 
	    	 //获得.的索引
	    	 int dot = filename.lastIndexOf('.'); 
	         if ((dot >-1) && (dot < (filename.length() - 1))) 
	         {
	        	 return filename.substring(dot + 1); 
	         } 
	     } 
	     return filename; 
	 } 
	 
}
