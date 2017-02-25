package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
FUNCTION:
    TeleOp
*/

@TeleOp(name = "TeleOp", group = "TeleOp")
public class TeleOpIterative extends OpMode
{
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry);

    private final ElapsedTime runtime = new ElapsedTime();

    private double LeftFY;
    private double LeftBY;
    private double RightFY;
    private double RightBY;

    private double LiftY;
    private double LaunchY;

    private double CollectLT;
    private double CollectUT;

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init()
    {
        telemetry.addData("Initialization","Starting TeleOp Initialization ...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        cmds.InitializeHW(robot);

        telemetry.addData("Initialization", "TeleOp Initialization Complete!");
        telemetry.update();
    }

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop()
    {
    }

    /* Code to run ONCE when the driver hits PLAY */
    @Override
    public void start()
    {
        telemetry.addData("Run Mode", "Starting Run Mode ...");
        telemetry.update();

        runtime.reset();

//        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);
    }

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop()
    {
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.update();

        //Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        //Main Driver - Drive motor controls
        LeftFY = -gamepad1.left_stick_y;
        LeftBY = -gamepad1.left_stick_y;
        RightFY = -gamepad1.right_stick_y;
        RightBY = -gamepad1.right_stick_y;

        robot.motorFrontLeft.setPower(LeftFY);
        robot.motorFrontRight.setPower(RightFY);
        robot.motorBackLeft.setPower(LeftBY);
        robot.motorBackRight.setPower(RightBY);

        //Main Driver - Ball collect motor controls
        CollectLT = gamepad1.right_trigger -gamepad1.left_trigger;
        //CollectUT = gamepad1.right_trigger -gamepad1.left_trigger;

//        robot.motorCollect.setPower(CollectLT);
        //robot.motorCollectUpper.setPower(CollectUT);

        //Alternate Driver  - Fork lift motor controls
        LiftY = gamepad2.left_stick_y;

        //robot.motorLift.setPower(LiftY);

        //Alternate Driver - Ball launch motor controls
        LaunchY = gamepad2.right_stick_y;

//        robot.motorLaunch.setPower(LaunchY);


        //Main driver - servo controls
//        if (gamepad1.right_bumper)
//        {
//            robot.servoForkLeft.setPosition(Configuration.OPEN_FORK_SERVO_POS);
//            robot.servoForkRight.setPosition(Configuration.OPEN_FORK_SERVO_POS);
//        }
//        else
//        {
//            robot.servoForkLeft.setPosition(Configuration.CLOSED_FORK_SERVO_POS);
//            robot.servoForkRight.setPosition(Configuration.CLOSED_FORK_SERVO_POS);
//        }

        if (gamepad1.b)
        {
  //          robot.servoBallGate.setPosition(Configuration.OPEN_BALL_GATE_POS);
            cmds.Shoot(robot);
        }
//        else
 //       {
 //           robot.servoBallGate.setPosition(Configuration.CLOSED_BALL_GATE_POS);
 //       }

        //Alternate driver - servo controls
//        if (gamepad2.x)
//        {
//            cmds.ReadyBeaconArm(robot);
//        }
//        else
//        {
//            cmds.DisarmBeaconArm(robot);
//        }

        //Alternate Driver - One button push to launch ball
//        if (gamepad2.b)
//        {
//            cmds.LaunchBallAndReset(robot, Configuration.LAUNCH_POWER);
//        }

        // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
        //cmds.waitForTick(40);
    }

    /* Code to run ONCE after the driver hits STOP */
    @Override
    public void stop()
    {
        telemetry.addData("Run Mode", "Run Mode Complete!");
        telemetry.update();
    }
}
