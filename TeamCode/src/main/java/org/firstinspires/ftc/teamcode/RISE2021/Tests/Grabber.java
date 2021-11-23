package org.firstinspires.ftc.teamcode.RISE2021.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RISE2021.Subsytems.UltronComponents;

/**
 * Created by Jordan Nuthalapaty  on 10/14/21
 */

@TeleOp(name = "2021 Teleop" , group = "2021")
public class Grabber extends OpMode {

    private UltronComponents component;

    private boolean isReady = false;

    @Override
    public void init() {

        //Initialize robot


        component = new UltronComponents(hardwareMap);
        component.init();

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
    public void start() {
        super.start();

    }

    @Override
    public void loop() {


        //----------------------------------------------=+(Intake)+=----------------------------------------------\\
        if (gamepad1.a) {
            component.intake();
        } else if (gamepad1.b) {
            component.outtake();
        }
    }
}