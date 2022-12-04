package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.RoadrunnerPointDataset;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.modules.opencv.SignalDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

@Config
@Autonomous(name = "AutoOp State Machines", group = "16481-Power-Play")
public class AutoopStateMachines extends LinearOpMode {


    boolean CYCLE = false;
    public enum STATE_POSITION {
        STATE_POSITION_SP9,
        STATE_POSITION_SP0,
        STATE_POSITION_SP1,
        STATE_POSITION_SP2,
        STATE_POSITION_SP3
    }

    public STATE_POSITION RobotPosition = STATE_POSITION.STATE_POSITION_SP9;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        OpenCvCamera camera;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().
                getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.
                get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        SignalDetection mySignalDetection = new SignalDetection(camera, (MultipleTelemetry) telemetry);
        mySignalDetection.openConnection();

        sleep(100);
        int tagID=-1;

        telemetry.addData("# Detecting AprilTag ","");
        telemetry.update();

        RobotPosition = STATE_POSITION.STATE_POSITION_SP2;
        while(!isStopRequested() && !opModeIsActive()) {
            tagID = mySignalDetection.CheckSignal();
            telemetry.addData("# Tag ID: ", tagID);
            telemetry.addData("Position selected ", RobotPosition);
            telemetry.update();
        }

        camera.closeCameraDevice();

        // **********************************************************************
        // **********************************************************************
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RoadrunnerPointDataset Trajectories = new RoadrunnerPointDataset(drive, (MultipleTelemetry) telemetry);


        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            switch (RobotPosition) {
                case STATE_POSITION_SP0:

                    drive.setPoseEstimate(Trajectories.S0_POS);
                    if(tagID == 0){ Trajectories.S0PP1(); }
                    else if (tagID == 1) { Trajectories.S0PP2(); }
                    else if (tagID == 2){  Trajectories.S0PP3(); }
                    RobotPosition = STATE_POSITION.STATE_POSITION_SP9;
                    break;
                case  STATE_POSITION_SP1:
                    drive.setPoseEstimate(Trajectories.S1_POS);
                    if(tagID == 0){ Trajectories.S1PP1(); }
                    else if (tagID == 1) { Trajectories.S1PP2(); }
                    else if (tagID == 2) { Trajectories.S1PP3(); }
                    RobotPosition = STATE_POSITION.STATE_POSITION_SP9;
                    break;
                case STATE_POSITION_SP2:
                    drive.setPoseEstimate(Trajectories.S2_POS);
                    if (tagID == 0) { Trajectories.S2PP1(); }
                    else if (tagID == 1) { Trajectories.S2PP2(); }
                    else if (tagID == 2) { Trajectories.S2PP3(); }
                    RobotPosition = STATE_POSITION.STATE_POSITION_SP9;
                    break;
                case STATE_POSITION_SP3:
                    drive.setPoseEstimate(Trajectories.S3_POS);
                    if(tagID == 0){ Trajectories.S3PP1(); }
                    else if (tagID == 1) { Trajectories.S3PP2(); }
                    else if (tagID == 2) { Trajectories.S3PP3(); }
                    RobotPosition = STATE_POSITION.STATE_POSITION_SP9;
                    break;
            }

        }
    }
}