package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Red/Blue Basic Long without Encoders", group="Red/Blue Autonomous")
class AutonomousBasicLongWithTime extends LinearOpMode
{
    private Hardware robot = new Hardware();
    private RobotConfiguration configs = new RobotConfiguration();
    private RobotCommands cmds = new RobotCommands();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode()
    {
        robot.IdentifyHardware(hardwareMap);

        /* Available commands: (preface each w/ cmds.)*/
        //  Initialize();
        //  DriveForward(DRIVE_POWER)/
        //  DriveReverse(DRIVE_POWER);
        //  EncoderDrive(DRIVE_POWER, <speed>, <leftinches>, <rightinches>, <timeS>);
        //  StopDriving();
        //  TurnLeft(TURN_POWER);
        //  TurnRight(TURN_POWER)/
        //  LaunchBallAndReset(LAUNCH_POWER);
        //  DropNewBall();
        //  CalibrateGyro();
        //  FindBeacon();
        //  ReportBeaconColor();

        telemetry.addData("Status","Autonomous Starting...");
        telemetry.update();

        configs.loadParameters();

        configs.InitializeHW();

        waitForStart();

        cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

        cmds.DropNewBall();

        cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

        //If we were going to go for a beacon, we would add code to move to the beacon and sense color here
        //cmds.TurnLeft(TURN_POWER);
        //sleep(1000);
        //cmds.ReadyBeaconArm();
        //cmds.ReportBeaconColor();

        //If we were going to go for the beacon, we would leave this wait out
        //wait 10 seconds in case we cross zones
        sleep(configs.AUTO_DELAY_TIME);

        //To drive forward with time:
        cmds.DriveForward(configs.DRIVE_POWER);
        sleep(configs.LONG_AUTO_DRIVE_TIME);
        // -OR
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < configs.LONG_AUTO_DRIVE_TIME)) {
//            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }

        cmds.StopDriving();

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
