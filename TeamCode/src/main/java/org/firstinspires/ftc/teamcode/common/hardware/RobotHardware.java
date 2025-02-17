package org.firstinspires.ftc.teamcode.common.hardware;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.drive.drivetrain.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.common.drive.localizer.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.util.InverseKinematics;
import org.firstinspires.ftc.teamcode.common.util.wrappers.WActuatorGroup;
import org.firstinspires.ftc.teamcode.common.util.wrappers.WEncoder;
import org.firstinspires.ftc.teamcode.common.util.wrappers.WServo;
import org.firstinspires.ftc.teamcode.common.util.wrappers.WSubsystem;
import org.firstinspires.ftc.teamcode.common.vision.PreloadDetectionPipeline;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.concurrent.GuardedBy;

@Config
public class RobotHardware {

    //drivetrain
    public DcMotorEx dtFrontLeftMotor;
    public DcMotorEx dtFrontRightMotor;
    public DcMotorEx dtBackLeftMotor;
    public DcMotorEx dtBackRightMotor;

    // extension
    public AbsoluteAnalogEncoder armPitchEncoder;
    public AnalogInput armPitchEnc;

    public DcMotorEx extensionMotor;
    public DcMotorEx extensionMotor2;
    public DcMotorEx armMotor;
    public DcMotorEx armMotor2;

    public WEncoder extensionEncoder;

    public WActuatorGroup armActuator;
    public WActuatorGroup extensionActuator;
    public WActuatorGroup intakePivotActuator;

    public WServo armLiftServo;

    public CRServoImplEx intakeContinousLeftServo;
    public WServo intakePivotLeftServo;
    public WServo intakePivotRightServo;
    public WServo ptoServo;

    private HardwareMap hardwareMap;

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    private ElapsedTime voltageTimer = new ElapsedTime();
    private double voltage = 12.0;

    private static RobotHardware instance = null;
    private boolean enabled;

    public List<LynxModule> modules;
    public LynxModule CONTROL_HUB;


    private ArrayList<WSubsystem> subsystems;

    public IntakeSubsystem intake;
    public ExtensionSubsystem extension;
    public MecanumDrivetrain drivetrain;

    public PreloadDetectionPipeline preloadDetectionPipeline;

    private final Object imuLock = new Object();
    @GuardedBy("imuLock")
    public BNO055IMU imu;
    private Thread imuThread;
    public GoBildaPinpointDriver localizer;


    public HashMap<Sensors.SensorType, Object> values;

    public static RobotHardware getInstance() {
        if (instance == null) {
            instance = new RobotHardware();
        }
        instance.enabled = true;
        return instance;
    }

    /**
     * Created at the start of every OpMode.
     *
     * @param hardwareMap The HardwareMap of the robot, storing all hardware devices
     */
    public void init(final HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.values = new HashMap<>();

        values.put(Sensors.SensorType.EXTENSION_ENCODER, 0);
        values.put(Sensors.SensorType.ARM_ENCODER, 0.0);
        values.put(Sensors.SensorType.PINPOINT_X, 0.0);
        values.put(Sensors.SensorType.PINPOINT_Y, 0.0);
        values.put(Sensors.SensorType.PINPOINT_ANG, 0.0);

        // DRIVETRAIN
        this.dtBackLeftMotor = hardwareMap.get(DcMotorEx.class, "dtBackLeftMotor");
        dtBackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.dtFrontLeftMotor = hardwareMap.get(DcMotorEx.class, "dtFrontLeftMotor");
        dtFrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.dtBackRightMotor = hardwareMap.get(DcMotorEx.class, "dtBackRightMotor");
        dtBackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dtBackRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.dtFrontRightMotor = hardwareMap.get(DcMotorEx.class, "dtFrontRightMotor");
        dtFrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dtFrontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                extensionMotor = hardwareMap.get(DcMotorEx.class, "extensionMotor");
        extensionMotor2 = hardwareMap.get(DcMotorEx.class, "extensionMotor2");
        armMotor = hardwareMap.get(DcMotorEx.class, "extensionPitchMotor");
        armMotor2 = hardwareMap.get(DcMotorEx.class, "extensionPitchMotor2");
        extensionMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        extensionMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        extensionEncoder = new WEncoder(new MotorEx(hardwareMap, "extensionMotor").encoder);

        this.armPitchEnc = hardwareMap.get(AnalogInput.class, "extensionPitchEncoder");
        this.armPitchEncoder = new AbsoluteAnalogEncoder(armPitchEnc);
        armPitchEncoder.zero(2.086);
        armPitchEncoder.setInverted(true);
        armPitchEncoder.setWraparound(true);

        this.extensionActuator = new WActuatorGroup(
                () -> intSubscriber(Sensors.SensorType.EXTENSION_ENCODER), extensionMotor,extensionMotor2)
                .setPIDController(new PIDController(0.008, 0.0, 0.0004))
                .setFeedforward(WActuatorGroup.FeedforwardMode.CONSTANT, 0.0)
//                .setMotionProfile(0, new ProfileConstraints(1000, 5000, 2000))
                .setErrorTolerance(20);

        this.armActuator = new WActuatorGroup(
                () -> doubleSubscriber(Sensors.SensorType.ARM_ENCODER), armMotor, armMotor2)
                .setPIDController(new PIDController(1.7500, 0, 0.05))
                .setFeedforward(WActuatorGroup.FeedforwardMode.CONSTANT, 0.0)
                .setErrorTolerance(0.03);

        armLiftServo = new WServo(hardwareMap.get(Servo.class, "lift"));

        // INTAKE
        this.intakePivotLeftServo = new WServo(hardwareMap.get(Servo.class, "servo1"));
        this.intakePivotRightServo = new WServo(hardwareMap.get(Servo.class, "servo2"));
        this.intakeContinousLeftServo = hardwareMap.get(CRServoImplEx.class, "servo3");
        intakePivotRightServo.setOffset(-0.03);
        intakePivotLeftServo.setOffset(-0.045);
        intakePivotRightServo.setDirection(Servo.Direction.REVERSE);

        this.intakePivotActuator = new WActuatorGroup(intakePivotLeftServo, intakePivotRightServo);
        intakePivotActuator.setOffset(-0.06);

        this.ptoServo = new WServo(hardwareMap.get(Servo.class, "servo4"));
        ptoServo.setOffset(-0.00);
        InverseKinematics.calculateTarget(0);

        modules = hardwareMap.getAll(LynxModule.class);

        this.preloadDetectionPipeline = new PreloadDetectionPipeline();

        for (LynxModule m : modules) {
            m.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            if (m.isParent() && LynxConstants.isEmbeddedSerialNumber(m.getSerialNumber())) CONTROL_HUB = m;
        }


        subsystems = new ArrayList<>();
        drivetrain = new MecanumDrivetrain();
        extension = new ExtensionSubsystem();
        intake = new IntakeSubsystem();
        if (Globals.IS_AUTO) {
            this.localizer = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
            localizer.setOffsets(142.50, 72.051);
            localizer.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
            localizer.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        }

        voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
    }

    public void read() {
        // Read all hardware devices here
        values.put(Sensors.SensorType.EXTENSION_ENCODER, extensionEncoder.getPosition());
        values.put(Sensors.SensorType.ARM_ENCODER, armPitchEncoder.getCurrentPosition());
        if (Globals.IS_AUTO) {
            values.put(Sensors.SensorType.PINPOINT_X, localizer.getEncoderX());
            values.put(Sensors.SensorType.PINPOINT_Y, localizer.getEncoderY());
            values.put(Sensors.SensorType.PINPOINT_ANG, localizer.getHeading());
        }
    }

    public void write() {
        extension.write();
        intake.write();
        drivetrain.write();
    }

    public void periodic() {
//        if (voltageTimer.seconds() > 5) {
//            voltageTimer.reset();
//            voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
//        }

        intake.periodic();
        extension.periodic();
        drivetrain.periodic();
        if (Globals.IS_AUTO) {
            localizer.periodic();
        }
    }

//    public void startIMUThread(LinearOpMode opMode) {
//        imuThread = new Thread(() -> {
//            while (!opMode.isStopRequested()) {
//                synchronized (imuLock) {
//                    imuAngle = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
//                }
//            }
//        });
//        imuThread.start();
//    }
//
//    public void readIMU() {
//        imuAngle = AngleUnit.normalizeRadians(imu.getAngularOrientation().firstAngle);
//    }
//
//    public double getAngle() {
//        return AngleUnit.normalizeRadians(imuAngle - imuOffset + startOffset);
//    }
//
//    public void reset() {
//        for (WSubsystem subsystem : subsystems) {
//            subsystem.reset();
//        }
//
//        imuOffset = imuAngle;
//    }
//
//    public void setIMUStartOffset(double off) {
//        startOffset = off;
//    }

    public void clearBulkCache() {
        CONTROL_HUB.clearBulkCache();
    }

    public void addSubsystem(WSubsystem... subsystems) {
        this.subsystems.addAll(Arrays.asList(subsystems));
    }


    public double getVoltage() {
        return voltage;
    }

    public double doubleSubscriber(Sensors.SensorType topic) {
        Object value = values.getOrDefault(topic, 0.0);
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        } else {
            throw new ClassCastException();
        }
    }

    public int intSubscriber(Sensors.SensorType topic) {
        Object value = values.getOrDefault(topic, 0);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Double) {
            return ((Double) value).intValue();
        } else {
            throw new ClassCastException();
        }
    }

    public boolean boolSubscriber(Sensors.SensorType topic) {
        return (boolean) values.getOrDefault(topic, 0);
    }

//    public List<AprilTagDetection> getAprilTagDetections() {
//        if (aprilTag != null && localizer != null) return aprilTag.getDetections();
//        System.out.println("Active");
//        return null;
//    }
//
//    public void startCamera() {
//        aprilTag = new AprilTagProcessor.Builder()
//                // calibrated using 3DF Zephyr 7.021
//                .setLensIntrinsics(549.651, 549.651, 317.108, 236.644)
//                .build();
//
//        visionPortal = new VisionPortal.Builder()
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .setCameraResolution(new Size(640, 480))
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
//                .addProcessors(aprilTag, preloadDetectionPipeline)
//                .enableLiveView(false)
//                .build();
//
//        visionPortal.setProcessorEnabled(preloadDetectionPipeline, false);
//    }
//
//    public VisionPortal.CameraState getCameraState() {
//        if (visionPortal != null) return visionPortal.getCameraState();
//        return null;
//    }
//
//    public void closeCamera() {
//        if (visionPortal != null) visionPortal.close();
//    }

    public void kill() {
        instance = null;
    }

//    public void setProcessorEnabled(VisionProcessor processor, boolean enabled) {
//        this.visionPortal.setProcessorEnabled(processor, enabled);
//    }
}
