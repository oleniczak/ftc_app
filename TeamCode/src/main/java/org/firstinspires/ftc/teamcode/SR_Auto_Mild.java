package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
FUNCTION:
    Autonomous
*/

@Autonomous(name="Mild no Gyro (Config Alliance & Position)", group="Autonomous")
//@Disabled
public class SR_Auto_Mild extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry);

    @Override
    public void runOpMode() {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        cmds.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(Configuration.AUTO_DELAY_TIME);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        //Move close enough to shoot balls
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_DIST_TO_SHOOT, Configuration.LONG_DIST_TO_SHOOT, 5.0);
        }
        else //SHORT
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_DIST_TO_SHOOT, Configuration.SHORT_DIST_TO_SHOOT, 5.0);
        }

        //turn on launch motor
//        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

        //Use delay until ball launch is ready for use
        //sleep(Configuration.AUTO_DELAY_TIME);

        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);
        robot.motorCollect.setPower(1.0);

        cmds.Shoot(robot);
        //cmds.Shoot(robot);

        robot.motorCollect.setPower(0);

        //Drive to center
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_DIST_TO_PARK, Configuration.LONG_DIST_TO_PARK, 5.0);
        }
        else
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_DIST_TO_PARK, Configuration.SHORT_DIST_TO_PARK, 5.0);
        }
        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
