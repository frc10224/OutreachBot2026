// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.kLeftBackID;
import static frc.robot.Constants.DrivetrainConstants.kLeftFrontID;
import static frc.robot.Constants.DrivetrainConstants.kRightBackID;
import static frc.robot.Constants.DrivetrainConstants.kRightFrontID;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private DifferentialDrive drivetrain;

  private Timer timer = new Timer();

  /** Creates a new Drivetrain. */
  @SuppressWarnings("resource")
  public Drivetrain() {
    // CHECK MOTORTYPE BEFORE DEPLOYING CODE
    SparkMax leftFrontMotor = new SparkMax(kLeftFrontID, MotorType.kBrushed);
    SparkMax rightFrontMotor = new SparkMax(kRightFrontID, MotorType.kBrushed);
    SparkMax leftBackMotor = new SparkMax(kLeftBackID, MotorType.kBrushed);
    SparkMax rightBackMotor = new SparkMax(kRightBackID, MotorType.kBrushed);

    SparkMaxConfig leftFrontConfig = new SparkMaxConfig();
    SparkMaxConfig rightFrontConfig = new SparkMaxConfig();
    SparkMaxConfig leftBackConfig = new SparkMaxConfig();
    SparkMaxConfig rightBackConfig = new SparkMaxConfig();

    leftFrontConfig.inverted(false);
    rightFrontConfig.inverted(true);
    leftBackConfig.follow(leftFrontMotor, false);
    rightBackConfig.follow(rightFrontMotor, true);

    leftFrontConfig.idleMode(IdleMode.kBrake);
    rightFrontConfig.idleMode(IdleMode.kBrake);
    leftBackConfig.idleMode(IdleMode.kBrake);
    rightBackConfig.idleMode(IdleMode.kBrake);

    leftFrontMotor.configure(leftFrontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFrontMotor.configure(rightFrontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftBackMotor.configure(leftBackConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightBackMotor.configure(rightBackConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    drivetrain = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private BooleanSupplier timerFinished = () -> {
    return timer.get() > 2;
  };

  public Command drive(Supplier<Double> xSpeed, Supplier<Double> zRotation) {
    return Commands.run(
      () -> {
        drivetrain.arcadeDrive(xSpeed.get(), zRotation.get(), true);
      },
      this
    );
  }

  public Command auto() {
    return Commands.startRun(
      () -> {timer.restart();},
      () -> {drivetrain.arcadeDrive(0, 0.2);},
      this
    ).until(timerFinished);
  }
}
