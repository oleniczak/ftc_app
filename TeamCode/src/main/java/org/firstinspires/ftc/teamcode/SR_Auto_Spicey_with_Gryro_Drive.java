package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * 2/12/2017 9019
 */

@Autonomous(name="Spicey Gyro Drive(Config Alliance & Position)", group="Autonomous")
@Disabled
class SR_Auto_Spicey_with_Gryro_Drive extends LinearOpMode {
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

        //Move close enough to shoot balls (assumes SHORT position)
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 6, 6, 5.0);
        cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 6, 0, 5.0);

        //turn on launch motor
//        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

        cmds.Shoot(robot);

        cmds.Shoot(robot);

//        robot.motorLaunch.setPower(0);

        //Drive to line up with closest beacon (assumes SHORT position)
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 70, 70, 5.0);
        cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 70, 0, 5.0);

        //Turn 90 degrees to face beacon
        //***** REPLACE WITH GYRO READINGS *****
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
            //cmds.GyroTurn(robot,Configuration.TURN_POWER,-90);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
            //cmds.GyroTurn(robot,Configuration.TURN_POWER,90);
        }

        //Drive into beacon, pressing button
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER,  52,  52, 5.0);
        cmds.GyroDrive(robot, Configuration.DRIVE_POWER,  52,  0, 5.0);

        //Backup 3 inches to assess color
        //cmds.EncoderDrive(robot,-Configuration.APPROACH_SPEED, 3, 3, 5.0);
        cmds.GyroDrive(robot,-Configuration.APPROACH_SPEED, 3, 0, 5.0);

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
//        cmds.SenseBeacon(robot);

        //Turn 90 degrees to drive to second beacon
        //***** REPLACE WITH GYRO READINGS *****
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        }

        //Drive to second beacon
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 40, 40, 5.0);
        cmds.GyroDrive(robot, Configuration.DRIVE_POWER, 40, 0, 5.0);

        //Turn 90 degrees to face beacon
        //***** REPLACE WITH GYRO READINGS *****
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        }

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
//        cmds.SenseBeacon(robot);

        cmds.StopDriving(robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}
