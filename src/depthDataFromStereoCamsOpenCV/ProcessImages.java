/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depthDataFromStereoCamsOpenCV;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import marvin.image.MarvinImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import marvin.gui.MarvinImagePanel;
import marvin.plugin.MarvinImagePlugin;

import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.Rectangle;



public class ProcessImages {
	//declarations
		static MarvinImagePlugin invert   = null;
		
//		private static newDetectBlobs blobDetector = null;
		
		/**
		 * 
		 * @param image
		 * @return
		 */
		public static Mat bringImageToStdSize(Mat image){
			//create the square container
		    int dstWidth = 300;
		    int dstHeight = 500;
		    Mat dst = new Mat(dstHeight, dstWidth, CvType.CV_8UC3, new Scalar(0,0,0));
		    //ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(dst),"background");
		    //Put the image into the container, roi is the new position
		    Rect roi= new Rect((dstWidth-image.cols())/2,(dstHeight-image.rows())/2,image.cols(),image.rows());
		    Mat targetROI = new Mat(dst,roi);		    
		    image.copyTo(targetROI);
//		    ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(dst),"Standardized");
		    return dst;
		}
		
		
		
		
		
		
		/**
		 * Trims image by trimSize
		 * @param image
		 * @param trimSize
		 * @return
		 */
		public static Mat  cropImageHorizontal(Mat image, int trimSize){
			
//			System.out.println("Initial image width "+image.width());
//			System.out.println("Initial image height "+image.height());
			
			Rect roi = new Rect(2*trimSize, 0,image.width()-4*trimSize, image.height());
			
			Mat result= image.submat(roi);
			
//			System.out.println("Trimmed image width "+ result.width());
//			System.out.println("Trimmed image height "+result.height());
//			displayImage(ProcessImages.Mat2BufferedImage(result),"Cropped  Image");
			return result;
			
		}
		
		
		
		/**
		 * BufferedImage2Mat
		 * @param BufferedImage image
		 * @return Mat
		 */
		public static Mat BufferedImage2Mat(BufferedImage image){
			//source: http://stackoverflow.com/questions/18581633/fill-in-and-detect-contour-rectangles-in-java-opencv
	        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	        Mat mat = new Mat(image.getHeight(),image.getWidth(), CvType.CV_8U);
	        mat.put(0, 0, data);
	        return mat;
	    }
		
		
		
		/**
		 * Mat2BufferedImage
		 * @param Mat m
		 * @return BufferedImage
		 */
		
		public static BufferedImage Mat2BufferedImage(Mat m){
			// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
			// Fastest code
			// The output can be assigned either to a BufferedImage or to an Image

			    int type = BufferedImage.TYPE_BYTE_GRAY;
			    if ( m.channels() > 1 ) {
			        type = BufferedImage.TYPE_3BYTE_BGR;
			    }
			    int bufferSize = m.channels()*m.cols()*m.rows();
			    byte [] b = new byte[bufferSize];
			    m.get(0,0,b); // get all the pixels
			    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
			    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			    System.arraycopy(b, 0, targetPixels, 0, b.length);  
			    return image;

			}
		
		/**
		 * to display images overloaded function to take BufferedImage as argument
		 * modified from initial source http://answers.opencv.org/question/31505/how-load-and-display-images-with-java-using-opencv/
		 * @param img2 -- image to display
		 * @param str -- description of image will appear as title of image frame
		 */
		public static void displayImage(BufferedImage img2, String str)
		{   
			MarvinImagePanel	imagePanel;
			imagePanel = new MarvinImagePanel();
			JFrame frame=new JFrame();       
		    frame.setSize(img2.getWidth()+20, img2.getHeight()+50);     
		    frame.add(imagePanel, BorderLayout.CENTER);
		    frame.setVisible(true);
		    frame.setTitle(str);
		    imagePanel.setImage(new MarvinImage(img2));
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	
		
		/**
		 * Applies Canny edge detection algorithm
		 * @param Mat image
		 * @return Mat
		 */
		public static Mat applyCannyEdgeDetectorOpenCV_Mat(Mat image) {
			//TODO
			//all these parameters have to be investigated
			Mat result=image.clone();
			int lowThreshold=40;
		    int ratio = 120;
		    int kernel_size = 3;
		    
		    Imgproc.blur(result, result,new Size(2,2));
		    Imgproc.Canny(result, result, lowThreshold, ratio, kernel_size,true);
		    //Imgproc.threshold(result,result,64, 254,Imgproc.THRESH_BINARY_INV);
		    return result;
		}
		
		
//		/**
//		 * remove the background of new image captured nowX.jpg
//		 * first crop the image to remove IR LED artifacts
//		 * then apply filters to remove the background
//		 * 
//		 * @param originalImagePath original image to be processed
//		 * @param imagePath path of the image to apply filters
//		 * @param bgRemovedImagePath background removed image path
//		 */
//		public void removeBackground(String originalImagePath, String croppedImagePath, String bgRemovedImagePath) { 
//			BufferedImage croppedImg = null;
//			try {
//				//apply basic image operations
//				croppedImg = new ImageSegmentation().cropImage(ImageIO.read(new File(originalImagePath)), 40, 20, 600, 450);
//				ImageIO.write(croppedImg, "PNG", new File(croppedImagePath));
//				
//				//apply filters to remove background and save outputs
//				new ApplyImageFilters().applyFilters(croppedImagePath, bgRemovedImagePath);
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		/**
		 * split the shoe images into two
		 * @param cannyEdgeDetectedImagePath
		 * @param shoesArea
		 * @param sLeftShoeExtractedImagePath
		 * @param sRightShoeExtractedImagePath
		 * @throws IOException
		 */
		//changed to static
		public static void splitLeftAndRightShoesFromCanny(BufferedImage bufCannyImg, int[] shoesArea, BufferedImage sLeftShoeExtractedImage, BufferedImage sRightShoeExtractedImage) throws IOException {
			//BufferedImage bufCannyImg = ImageIO.read(new File(cannyEdgeDetectedImagePath));
			int x = 0;//shoesArea[0];
			int y = 0;//shoesArea[1];
			int w = shoesArea[2];
			int h = shoesArea[3];
			int iOrientationOfShoes = shoesArea[4];
			
			//save single shoes
			if ( iOrientationOfShoes == 1 ) { //vertical
				sLeftShoeExtractedImage=bufCannyImg.getSubimage(x, y, ((w/2)), h);//getSubimage(x,y,bufCannyImg.getSubimage(x, y, ((w/2)), h).getWidth(), bufCannyImg.getSubimage(x, y, ((w/2)), h).getHeight());
				sRightShoeExtractedImage=bufCannyImg.getSubimage( ((w/2)-5), y, (w/2), h);//sRightShoeExtractedImage.getSubimage(((w/2)-5), y,bufCannyImg.getSubimage( ((w/2)-5), y, (w/2), h).getWidth(), bufCannyImg.getSubimage( ((w/2)-5), y, (w/2), h).getHeight());				
//				ImageIO.write(sLeftShoeExtractedImage, "PNG", new File(sLeftShoeExtractedImagePath));
//				ImageIO.write(sRightShoeExtractedImage, "PNG", new File(sRightShoeExtractedImagePath));
			}
			else if ( iOrientationOfShoes == 0 ) { // Horizontal
				sLeftShoeExtractedImage=bufCannyImg.getSubimage(x, y, w, ((h/2)));//sLeftShoeExtractedImage.getSubimage(0,0,bufCannyImg.getSubimage(x, y, w, ((h/2))).getWidth(), bufCannyImg.getSubimage(x, y, w, ((h/2))).getHeight());
				sRightShoeExtractedImage=bufCannyImg.getSubimage(x, ((h/2)-5), w, h/2);//sRightShoeExtractedImage.getSubimage(0,0,bufCannyImg.getSubimage(x, ((h/2)-5), w, h/2).getWidth(), bufCannyImg.getSubimage(x, ((h/2)-5), w, h/2).getHeight());
//				ImageIO.write(sLeftShoeExtractedImage, "PNG", new File(sLeftShoeExtractedImagePath));
//				ImageIO.write(sRightShoeExtractedImage, "PNG", new File(sRightShoeExtractedImagePath));
				
				
			}
			ProcessImages.displayImage(sLeftShoeExtractedImage,"Left Shoe");
			ProcessImages.displayImage(sRightShoeExtractedImage,"Right Shoe");
		}
//		/**
//		 * detect the shoe blobs and extract shoes only region from the image and save it
//		 * (put all the blobs larger than 100 x 200 into an array in descending order of their heights)
//		 * @param croppedImagePath cropped original image of the shoes
//		 * @param outlineDetectedImagePath outline detected image with sobel edge detector
//		 * @param shoesOnlyImagePath shoes only image to be saved
//		 * @return 
//		 * @throws IOException
//		 */
//		public static int[] extractShoeSolesRegion(BufferedImage croppedImage) throws IOException {
//			blobDetector = new newDetectBlobs();
//			return blobDetector.extractShoesOnlyRegion(croppedImage);
//		}
		
		
		/**
		 * takes a Mat image as an output and convert it to a binary array (0 for black and 1 for white pixel)	
		 * @param image
		 * @return
		 */
		static int[][] extractBinaryDataFromImage(Mat image){
			  BufferedImage inputImage = ProcessImages.Mat2BufferedImage(image);
			  int sizeX = inputImage.getWidth();
			  int sizeY = inputImage.getHeight();
			  int [][] iarrImageColors             = new int[sizeX][sizeY];
			  //get B & W pixel data = thresholding
			  for (int x = 0; x < sizeX; x++) {
					for (int y = 0; y < sizeY; y++) {
						int rgb = inputImage.getRGB(x, y);
						// if the RGB value at given point x,y is greater than 16 then set that pixel to white else set to black
						int r = (rgb >> 16) & 0xFF;
						int g = (rgb >> 8) & 0xFF;
						int b = (rgb & 0xFF);
						int gray = (r + g + b) / 3;

						if (gray > 230) iarrImageColors[x][y] = 1; //white  
						else            iarrImageColors[x][y] = 0; //black  
					}
					
				}
			  return iarrImageColors;
		}
		
		
		
		
		
		
		
		
		
}

