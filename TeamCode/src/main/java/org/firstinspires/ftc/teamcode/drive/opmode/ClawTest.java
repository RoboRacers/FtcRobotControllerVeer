

package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "ClawTest")
public class ClawTest extends LinearOpMode {

    Servo claw;

    @Override
    public void runOpMode() {

        // Connect to servo (Assume Robot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        claw = hardwareMap.get(Servo.class, "claw");

        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){
            if(gamepad1.dpad_up) {
                claw.setPosition(0);
                gamepad1.rumble(500);
            } else if(gamepad1.dpad_down) {
                claw.setPosition(1);
                gamepad1.rumble(500);
            } else {
                claw.setPosition(0.5);
            }
        }


    }
}
