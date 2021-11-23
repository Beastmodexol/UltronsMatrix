package org.firstinspires.ftc.teamcode.RISE2021.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.RISE2021.Subsytems.UltronDrivetrain;

/**
 * Created by Jordan Nuthalapaty on 10/27/21
 */

@TeleOp(name = "Ultron Teleop", group = "Ultron")
public class UltronTeleop extends OpMode {

    private UltronDrivetrain robot;


    private boolean isReady = false;
//ultron
    @Override
    public void init() {

        //Initialize robot
        robot = new UltronDrivetrain(hardwareMap);
        robot.runUsingEncoders();


        isReady = true;
    }

    @Override
    public void init_loop() {
        if (isReady) {
            telemetry.addData(">", "Robot Ready!");
            telemetry.update();
        }
    }



    @Override
    public void loop() {


        //----------------------------------------------=+(Drivetrain)+=----------------------------------------------\\
        robot.drive(gamepad1, telemetry);

        if (gamepad1.x) {
            robot.resetHeading();
        }
    }
}

