package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.sequencesegment.TrajectorySegment;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class Autopathing extends LinearOpMode {
    DcMotorEx motorLeft;
    DcMotorEx motorRight;
    Servo claw;
    final int liftLow = 0;
    final int liftHigherThanLow = -600;
    final int liftMid = -900;
    final int liftHigh = -1200;
    final int highstackpreset = -400;
    Trajectory traj1;
    Trajectory traj2;
    Trajectory traj3;
    Trajectory traj4;
    final double close = 0.7;
    final double open =0;
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        motorLeft = hardwareMap.get(DcMotorEx.class, "LiftLeft");
        motorRight = hardwareMap.get(DcMotorEx.class, "LiftRight");
        claw = hardwareMap.get(Servo.class, "claw");

        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (isStopRequested()) return;
        Pose2d StartPose = new Pose2d(-36, 64.5, Math.toRadians(270));
        drive.setPoseEstimate(StartPose);
        traj1 = drive.trajectoryBuilder(StartPose)
                .lineTo(new Vector2d(-36, 36))
                .addSpatialMarker(new Vector2d(-36, 36),() -> {
                    claw(close);
                })
                .build();
        traj2 = drive.trajectoryBuilder(traj1.end())
                .strafeTo(new Vector2d(-24, 36))
                .addSpatialMarker(new Vector2d(-24, 36),() -> {
                    ArmPosition(liftMid);
                })
                .build();
        traj3 = drive.trajectoryBuilder(traj2.end())
                .lineTo(new Vector2d(-24, 32))
                .addSpatialMarker(new Vector2d(-24, 32),() -> {
                    claw.setPosition(open);
                })
                .build();
        traj4 = drive.trajectoryBuilder(traj3.end())
                .lineTo(new Vector2d(-24, 36))
                .addSpatialMarker(new Vector2d(-24, 36),() -> {
                    ArmPosition(liftLow);
                })
                .build();
        drive.followTrajectory(traj1);
        drive.followTrajectory(traj2);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
        while(opModeIsActive()) {
            drive.update();
        }
    }
    public void ArmPosition(int pos) {
        motorLeft.setPower(0);
        motorRight.setPower(0);
        motorRight.setTargetPosition(pos);
        motorLeft.setTargetPosition(pos);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setPower(1);
        motorRight.setPower(1);
    }
    public void claw(double pos) {
        claw.setPosition(pos);
    }
}
