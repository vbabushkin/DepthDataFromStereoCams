package depthDataFromStereoCamsOpenCV;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package testopencv;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.highgui.Highgui;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.calib3d.Calib3d;
//import org.opencv.calib3d.StereoBM;
///**
// *
// * @author Wild
// */
//public class TestOpenCV {
//    static String initialImagePath=".//camPictures//";
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
//        //read images
//        int num=47130;
//        String fileLeft=initialImagePath+"undistLeft"+num+".jpg";
//        String fileRight=initialImagePath+"undistRight"+num+".jpg";
//        Mat imgL  = Highgui.imread(fileLeft, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
//        Mat imgR  = Highgui.imread(fileRight, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
//
//        Core.normalize(imgL, imgL, 0, 255, 32, CvType.CV_8U);
//
//        Core.normalize(imgR, imgR, 0, 255, 32, CvType.CV_8U);
//        
//        ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(imgL),"Left Image");
//	ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(imgR),"Right Image");
//        // TODO code application logic here
//        
//        //StereoBM stereo = new StereoBM(StereoBM.FISH_EYE_PRESET);
//        StereoBM stereo = new StereoBM(StereoBM.BASIC_PRESET, 64,41);
////        StereoSGBM stereo = new StereoSGBM();
////        stereo.set_SADWindowSize(41);
////        stereo.set_numberOfDisparities(64);
////        stereo.set_preFilterCap(4);
////        stereo.set_minDisparity(16);
////        stereo.set_uniquenessRatio(5);
////        stereo.set_speckleWindowSize(15);
////        stereo.set_speckleRange(2);
////        stereo.set_disp12MaxDiff(100);
////        stereo.set_fullDP(false);
////        stereo.set_P1(600);
////        stereo.set_P2(3200);
//
//        Mat disparity = new Mat();
//        stereo.compute(imgL, imgR, disparity);
//
//        Mat thresholdedBinary=new Mat();
//        //Filters off small noise blobs in disparity map
//        Calib3d.filterSpeckles(disparity, 32, 25, 10);
//        Core.normalize(disparity, disparity, 0, 255, 32, CvType.CV_8U);
//
//        Highgui.imwrite(initialImagePath+"depthMap"+num+".jpg",disparity);
//
//        ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(disparity),"Depth Map");
//
//        org.opencv.core.Core.inRange(disparity, new Scalar(0, 0, 0), new Scalar(15, 15,15), thresholdedBinary);
//        //Imgproc.threshold(disparity, thresholdedBinary, 125, 160, Imgproc.THRESH_BINARY);
//        ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(thresholdedBinary),"Depth Map Thresholded");
//
//        Mat image= new Mat();
//        Imgproc.dilate(thresholdedBinary, image, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3)),new Point(0,0), 2);
//
//        ProcessImages.displayImage(ProcessImages.Mat2BufferedImage(image),"DILATED");
//    }
//    
//}
