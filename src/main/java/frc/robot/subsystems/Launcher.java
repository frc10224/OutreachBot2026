// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.kFlyWheelID;
import static frc.robot.Constants.LauncherConstants.kFlywheelSpeed;
import static frc.robot.Constants.LauncherConstants.kIntakeID;
import static frc.robot.Constants.LauncherConstants.kIntakeSpeed;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
  // CHECK MOTORTYPE BEFORE DEPLOYING CODE
  private SparkMax flyWheelMotor;
  private SparkMax intakeMotor;

  /** Creates a new Launcher. */
  public Launcher() {
    flyWheelMotor = new SparkMax(kFlyWheelID, MotorType.kBrushed);
    intakeMotor = new SparkMax(kIntakeID, MotorType.kBrushed);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command runFlywheel() {
    return Commands.runEnd(
      () -> {
        flyWheelMotor.set(kFlywheelSpeed);
      },
      () -> {
        flyWheelMotor.set(0);
      },
      this
    );
  }

  public Command runIntake() {
    return Commands.runEnd(
      () -> {
        intakeMotor.set(kIntakeSpeed);
      },
      () -> {
        intakeMotor.set(0);
      },
      this
    );
  }
}
