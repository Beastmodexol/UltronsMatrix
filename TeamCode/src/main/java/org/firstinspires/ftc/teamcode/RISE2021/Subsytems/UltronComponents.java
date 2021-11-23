package org.firstinspires.ftc.teamcode.RISE2021.Subsytems;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Jordan Nuthalapaty on 10/14/2021.
 */
public class UltronComponents {

    private CRServo RGrab;
    private CRServo LGrab;




    public UltronComponents(final HardwareMap hardwareMap) {

        RGrab = hardwareMap.get(CRServo.class, "prototype");
        LGrab = hardwareMap.get(CRServo.class, "prototypeL");
        LGrab.setDirection(CRServo.Direction.FORWARD);
        RGrab.setDirection(CRServo.Direction.FORWARD);

    }
    public void init() {

       RGrab.setPower(0);
       LGrab.setPower(0);
    }


    //----------------------------------------------=+(Intake)+=----------------------------------------------\\
    public void intake() {
        RGrab.setPower(-1);
        LGrab.setPower(1);
    }
    public void outtake() {
        RGrab.setPower(1);
        LGrab.setPower(-1);
    }


}

