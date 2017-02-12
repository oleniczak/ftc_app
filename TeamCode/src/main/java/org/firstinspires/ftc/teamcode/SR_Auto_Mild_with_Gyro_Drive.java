package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
FUNCTION:
    Autonomous
*/

@Autonomous(name="Mild Gyro Drive (Config Alliance & Position)", group="Autonomous")
//@Disabled
class SR_Auto_Mild_with_Gyro_Drive extends LinearOpMode
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
            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 36, 36, 5.0);
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 36, 0, 5.0);
        }
        else //SHORT
        {
            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 6, 6, 5.0);
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 6, 0, 5.0);
        }

        //turn on launch motor
//        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

        cmds.DropAndShoot(robot);

        cmds.DropAndShoot(robot);

//        robot.motorLaunch.setPower(0);

        //Drive to center
        if (Configuration.START_POSITION.equals("LONG"))
        {
            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 60, 60, 5.0);
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 60, 0, 5.0);
        }
        else //SHORT
        {
            //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 40, 40, 5.0);
            cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 40, 0, 5.0);
        }

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
