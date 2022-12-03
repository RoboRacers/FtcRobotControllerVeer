package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "IntakeStates")
@Disabled
public class Intake extends LinearOpMode {
    public enum IStates{
        open,
        close,
    }
    public double servoOpen = 0;
    public double servoClose = 0.7;
    public IStates IntakeStates = IStates.open;
    Servo claw;

    @Override
    public void runOpMode() {

        claw = hardwareMap.get(Servo.class, "claw");

        waitForStart();
        if (isStopRequested()) return;

        while(opModeIsActive()){
            switch(IntakeStates) {
                case open:
                    claw.setPosition(0);
                    if(gamepad2.left_trigger==1) {
                        IntakeStates = IStates.close;
                    }
                    break;
                case close:
                    claw.setPosition(0.7);
                    if(gamepad1.left_trigger==1) {
                        IntakeStates = IStates.open;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
