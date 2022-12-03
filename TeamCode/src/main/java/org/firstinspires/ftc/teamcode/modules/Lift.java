package org.firstinspires.ftc.teamcode.modules;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "LiftStates")
public class Lift extends LinearOpMode {
    public enum LStates{
        ground,
        low,
        mid,
        high,
        idle
    }
    public boolean AtPos;
    public LStates LiftStates = LStates.idle;

    public double liftHighPos = 0;
    public double liftLowPos = 0;
    public double liftMidPos = 0;
    public double liftGroundPos = 0;

    DcMotor lift1;
    DcMotor lift2;

    @Override
    public void runOpMode() {
        lift1 = hardwareMap.get(DcMotor.class, "lift1");
        lift2 = hardwareMap.get(DcMotor.class, "lift2");

        waitForStart();
        if (isStopRequested()) return;

        while(opModeIsActive()){
            switch(LiftStates) {
                case ground:

                    break;
                case low:

                    break;
                case mid:

                    break;
                case high:

                    break;
                default:
                    break;
            }
        }
    }


}
