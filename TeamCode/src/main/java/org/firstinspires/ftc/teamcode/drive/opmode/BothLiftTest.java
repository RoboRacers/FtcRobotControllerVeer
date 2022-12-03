/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="BothLiftTest")
public class BothLiftTest extends LinearOpMode {

    private DcMotor motorLeft ;
    private  DcMotor motorRight;

    @Override
    public void runOpMode() {

        motorLeft  = hardwareMap.get(DcMotor.class, "LiftLeft");
        motorRight  = hardwareMap.get(DcMotor.class, "LiftRight");

        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        int leftTarget = -500;
        int rightTarget = -500;


        while (opModeIsActive()) {
            motorLeft.setPower(0);                                     motorRight.setPower(0);
            motorRight.setTargetPosition(rightTarget);        motorLeft.setTargetPosition(leftTarget);

            motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);    motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeft.setPower(.5);     motorRight.setPower(.5);

            while(motorLeft.isBusy() && motorRight.isBusy()) {
                // Display it for the driver.
                telemetry.addData("Currently at",  " at %7d :%7d",  motorLeft.getCurrentPosition(), motorRight.getCurrentPosition());
                telemetry.update();
            }

            if(leftTarget==0) leftTarget = -1000; else leftTarget = 0;
            if(rightTarget==0) rightTarget = -1000; else  rightTarget = 0;
        }
    }

}
//if(button_du){
//                rightSlidePower = 1;
//                leftSlidePower = 1;
//            }
//            if(button_dd){
//                rightSlidePower = -1;
//                leftSlidePower = -1;
//            }
//            if (!button_du && !button_dd){
//                rightSlidePower = 0;
//                leftSlidePower = 0;
//            }
//
//            // linear slide limits
//            if (robot.rightSlide.getCurrentPosition() > RIGHT_SLIDE_MAX && robot.leftSlide.getCurrentPosition() > LEFT_SLIDE_MAX) {
//                rightSlidePower = rightSlidePower > 0 ? 0 : rightSlidePower;
//                leftSlidePower = leftSlidePower > 0 ? 0 : leftSlidePower;
//            }
//            if (robot.rightSlide.getCurrentPosition() < -10 && robot.leftSlide.getCurrentPosition() < -10) {
//                rightSlidePower = rightSlidePower < 0 ? 0 : rightSlidePower;
//                leftSlidePower = leftSlidePower < 0 ? 0 : leftSlidePower;
//            }
//            if (robot.rightSlide.getCurrentPosition() < -20 && robot.leftSlide.getCurrentPosition() < -20) {
//                rightSlidePower = Math.max(0.1, leftSlidePower);
//                leftSlidePower = Math.max(0.1, leftSlidePower);
//            }
