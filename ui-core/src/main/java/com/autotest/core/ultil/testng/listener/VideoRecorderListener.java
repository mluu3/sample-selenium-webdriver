package com.autotest.core.ultil.testng.listener;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.AudioFormatKeys.EncodingKey;
import static org.monte.media.AudioFormatKeys.FrameRateKey;
import static org.monte.media.AudioFormatKeys.KeyFrameIntervalKey;
import static org.monte.media.AudioFormatKeys.MediaType;
import static org.monte.media.AudioFormatKeys.MediaTypeKey;
import static org.monte.media.AudioFormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.MIME_QUICKTIME;
import static org.monte.media.VideoFormatKeys.*;

public class VideoRecorderListener extends TestListenerAdapter {

    private static ScreenRecorder screenRecorder;
    boolean isRecordVideo;
    boolean isOnTestFail;
    private final File mavenProjectBuildDirectory = new File(System.getProperty("maven.project.build.directory", "./target/"));
    private final File videosOutputDir = new File(mavenProjectBuildDirectory, "videos/");
    private File recordingFile;

    public VideoRecorderListener() throws IOException, AWTException {
        isRecordVideo = Boolean.parseBoolean(System.getProperty("video.recording"));
        isOnTestFail = Boolean.parseBoolean(System.getProperty("video.onTestFail"));
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();

        screenRecorder = new ScreenRecorder(gc, null, new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null, videosOutputDir);
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (!isRecordVideo) {
            return;
        }

        try {
            screenRecorder.start();
            recordingFile = new File(videosOutputDir, result.getMethod().getMethodName() + ".mov");
            screenRecorder.getCreatedMovieFiles().get(0).renameTo(recordingFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isOnTestFail) {
            recordingFile.delete();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recordingFile.delete();
    }
}
