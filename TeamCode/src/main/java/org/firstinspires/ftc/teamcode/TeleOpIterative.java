/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "TeleOp", group = "TeleOp")
@Disabled
public class TeleOpIterative extends OpMode
{
    private final Hardware robot = new Hardware();
    private final RobotConfiguration configs = new RobotConfiguration();
    private final RobotCommands cmds = new RobotCommands();

    private final ElapsedTime runtime = new ElapsedTime();

    private double LeftFY;  //? local variable
    private double LeftBY;  //? local variable
    private double RightFY; //? local variable
    private double RightBY; //? local variable

    private double LiftY;   //? local variable
    private double LaunchY; //? local variable

    private double CollectLT;   //? local variable
    private double CollectUT;   //? local variable

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init()
    {
        robot.IdentifyHardware(hardwareMap);

        telemetry.addData("Status", "Starting TeleOp Initialization ...");
        telemetry.update();

        configs.loadParameters();

        configs.InitializeHW();

        cmds.InitializeServos();

        telemetry.addData("Status", "TeleOp Initialization Complete!");
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
        runtime.reset();
    }

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop()
    {
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.update();

        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        // leftMotor.setPower(-gamepad1.left_stick_y);
        // rightMotor.setPower(-gamepad1.right_stick_y);

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
        CollectUT = gamepad1.right_trigger -gamepad1.left_trigger;

        robot.motorCollectLower.setPower(CollectLT);
        robot.motorCollectUpper.setPower(CollectUT);

        //Alternate Driver  - Fork lift motor controls
        LiftY = gamepad2.left_stick_y;

        robot.motorLift.setPower(LiftY);

//******************************************************
        //Alternate Driver - Ball launch motor controls
        LaunchY = gamepad2.right_stick_y;

        robot.motorLaunch.setPower(LaunchY);
        //cmds.LaunchBallAndReset(configs.LAUNCH_POWER);

//******************************************************

        //Main driver - servo controls
        if (gamepad1.right_bumper)
        {
            robot.servoForkLeft.setPosition(configs.OPEN_FORK_SERVO_POS);
            robot.servoForkRight.setPosition(configs.OPEN_FORK_SERVO_POS);
        }
        else
        {
            robot.servoForkLeft.setPosition(configs.CLOSED_FORK_SERVO_POS);
            robot.servoForkRight.setPosition(configs.CLOSED_FORK_SERVO_POS);
        }

        if (gamepad1.b)
        {
            robot.servoBallGate.setPosition(configs.OPEN_BALL_GATE_POS);
            //cmds.DropNewBall();
        }
        else
        {
            robot.servoBallGate.setPosition(configs.CLOSED_BALL_GATE_POS);
        }

        //Alternate driver - servo controls
        if (gamepad2.x)
        {
            robot.servoButtonArm.setPosition(configs.DETECTION_BUTTON_POS);
            //cmds.ReadyBeaconArm();
        }
        else
        {
            robot.servoButtonArm.setPosition(configs.START_BUTTON_POS);
            //cmds.DisarmBeaconArm();
        }

        //Alternate Driver - One button push to launch ball
        if (gamepad2.b)
        {
            cmds.LaunchBallAndReset(configs.LAUNCH_POWER);
        }
        else
        {
            robot.motorLaunch.setPower(0);
            //robot.servoBallGate.setPosition(configs.CLOSED_BALL_GATE_POS);
        }



        // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
        robot.waitForTick(40);
    }

    /* Code to run ONCE after the driver hits STOP */
    @Override
    public void stop() {
    }
}
