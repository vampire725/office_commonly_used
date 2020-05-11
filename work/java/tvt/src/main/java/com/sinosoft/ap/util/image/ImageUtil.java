//package com.sinosoft.ap.util.image;
//
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;
//
//import javax.imageio.ImageIO;
//import javax.imageio.ImageReadParam;
//import javax.imageio.ImageReader;
//import javax.imageio.stream.ImageInputStream;
//
//import com.sinosoft.ap.common.exception.ServiceException;
//import com.sinosoft.ap.util.id.PrimaryKeyUtil;
//import com.sinosoft.ap.util.result.ResultConstant;
//
///**
// * 图片剪切工具 
// *
// */
//public class ImageUtil {
//	
//	/**
//	 * 
//	 * @param imageFile  图片文件
//	 * @param result    图片存放位置（绝对路径）
//	 * @param startX	剪切起点X坐标
//	 * @param startY	剪切起点Y坐标
//	 * @param endX		剪切终点X坐标
//	 * @param endY		剪切终点Y坐标
//	 */
//	public final static String cutImage(File imageFile, String result,
//			int startX, int startY,
//	        int endX, int endY) {
//		try {
//			String separator = System.getProperty("file.separator");
//			String a = imageFile.getName().substring(imageFile.getName().lastIndexOf(".")+1);
//	        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(a);/*PNG,BMP*/   
//	        ImageReader reader = (ImageReader)iterator.next();/*获取图片尺寸*/
////	        System.out.println(reader.getHeight(0));
////	        System.out.println(reader.getWidth(0));
//	        InputStream inputStream = new FileInputStream(imageFile);  
//	        ImageInputStream iis = ImageIO.createImageInputStream(inputStream);   
//	        reader.setInput(iis, true);   
//	        ImageReadParam param = reader.getDefaultReadParam();   
//	        Rectangle rectangle = new Rectangle(startX, startY, endX, endY);/*指定截取范围*/    
//	        param.setSourceRegion(rectangle);
//	        String name = PrimaryKeyUtil.create();
//	        result = result.endsWith(separator)?result:result+separator;
//	        File file = new File(result+name+"."+a);
//	        if(!file.getParentFile().exists()) {
//	        	file.mkdirs();
//	        }
//	        BufferedImage bi = reader.read(0,param); 
//	        ImageIO.write(bi, a,file);
//	        inputStream.close();
//	        return result+name+"."+a;
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    	throw new ServiceException(ResultConstant.UPLOAD_FILE_FAIL);
//	    }
//	}
//	
//	public static void main(String[] args) throws Exception, IOException {
//		System.out.println("kaishi");
//		File picture = new File("E:/u=1794894692,1423685501&fm=117&gp=0.jpg");
//		System.out.println("kaishi");
//        BufferedImage sourceImg =ImageIO.read(new FileInputStream(picture)); 
//        System.out.println("kaishi");
//        System.out.println(String.format("%.1f",picture.length()/1024.0));
//        System.out.println("kaishi");
//        System.out.println(sourceImg.getWidth());
//        System.out.println("kaishi");
//        System.out.println(sourceImg.getHeight());
//	}
//	
//}
