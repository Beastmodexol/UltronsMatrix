package org.firstinspires.ftc.teamcode.RISE2021.Subsytems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class UltronDrivetrain {

    private final DcMotor lf, lr, rf, rr;

    private final BNO055IMU imu;

    private double headingOffset = 0.0;
    private Orientation angles;


    public UltronDrivetrain(final HardwareMap hardwareMap) {

        //configuring the components
        lr = hardwareMap.dcMotor.get("leftB");
        lf = hardwareMap.dcMotor.get("leftF");
        lr.setDirection(DcMotor.Direction.REVERSE);
        lf.setDirection(DcMotor.Direction.REVERSE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rr = hardwareMap.dcMotor.get("rightB");
        rf = hardwareMap.dcMotor.get("rightF");
        rr.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.FORWARD);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit            = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit            = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled       = true;
        parameters.useExternalCrystal   = true;
        parameters.mode                 = BNO055IMU.SensorMode.IMU;
        parameters.loggingTag           = "IMU";
        imu                             = hardwareMap.get(BNO055IMU.class, "imuINT");

        imu.initialize(parameters);
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);

    }

    private void setMotorMode(DcMotor.RunMode mode, DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    public void runUsingEncoders() {
        setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER, lf, lr, rf, rr);
    }

    /**
     * Fetch all once-per-time-slice values.
     * <p>
     * Call this either in your OpMode::loop function or in your while(opModeIsActive())
     * loops in your autonomous. It refresh gyro and other values that are computationally
     * expensive.
     */
    public void loop() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
    }

    /**
     * @return the raw heading along the desired axis
     */
    private double getRawHeading() {
        return angles.firstAngle;
    }

    /**
     * @return the robot's current heading in radians
     */
    private double getHeading() {
        return (getRawHeading() - headingOffset) % (2.0 * Math.PI);
    }

    /**
     * Set the current heading to zero.
     */
    public void resetHeading() {
        headingOffset = getRawHeading();
    }

    /**
     * Find the maximum absolute value of a set of numbers.
     *
     * @param xs Some number of double arguments
     * @return double maximum absolute value of all arguments
     */
    private static double maxAbs(double... xs) {
        double ret = Double.MIN_VALUE;
        for (double x : xs) {
            if (Math.abs(x) > ret) {
                ret = Math.abs(x);
            }
        }
        return ret;
    }

    /**
     * Set motor powers
     * <p>
     * All powers will be scaled by the greater of 1.0 or the largest absolute
     * value of any motor power.
     *
     * @param _lf Left front motor
     * @param _lr Left rear motor
     * @param _rf Right front motor
     * @param _rr Right rear motor
     */
    private void setMotors(double _lf, double _lr, double _rf, double _rr) {
        final double scale = maxAbs(1.0, _lf, _lr, _rf, _rr);
        lf.setPower(_lf / scale);
        lr.setPower(_lr / scale);
        rf.setPower(_rf / scale);
        rr.setPower(_rr / scale);
    }

    public void drive(Gamepad gamepad1, Telemetry telemetry) {
        loop();

        final double x = -gamepad1.left_stick_x;
        final double y = gamepad1.left_stick_y;

        final double rotation = (gamepad1.right_stick_x);
        final double direction = Math.atan2(x, y) + getHeading();
        final double speed = Math.min(1.0, Math.sqrt(x * x + y * y));

        final double lf = speed * Math.sin(direction + Math.PI / 4.0) + rotation;
        final double rf = speed * Math.cos(direction + Math.PI / 4.0) - rotation;
        final double lr = speed * Math.cos(direction + Math.PI / 4.0) + rotation;
        final double rr = speed * Math.sin(direction + Math.PI / 4.0) - rotation;

        if(gamepad1.right_bumper) {//If we are in a state where we want to quarter the drive speed then do so.
            setMotors(lf / 4, lr / 4, rf / 4, rr / 4);//Quarter Speed
        } else {//If we are not in a state where we want to quarter the drive speed well... then don't.
            setMotors(lf, lr, rf, rr );//Full Speed
        }

        /**
         * If Ultron steps out of line no one is safe. Connor must inject Ultron with the C Bot virus before it is too late.
         */

//        telemetry.addData("Speeds","%f,%f,%f,%f", lf,rf,lr,rr);
//        telemetry.addData("RAW Gyro: ",getRawHeading());
//        telemetry.addData("Heading: ",getHeading());
//        telemetry.addData("Offset: ",headingOffset);
//
//        telemetry.update();
    }
}


