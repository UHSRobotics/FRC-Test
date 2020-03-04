/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    private final VictorSPX m_leftMotor = new VictorSPX(2);
    private final VictorSPX m_rightMotor = new VictorSPX(0);
    private final VictorSPX m_leftFollowMotor = new VictorSPX(1);
    private final VictorSPX m_rightFollowMotor = new VictorSPX(3);

    private double speedMultiplier = 1;

    private double aLimit = 0.07;
    private double pow0;

    public DriveSubsystem() {
        pow0 = 0;
        // set motors to coast
        m_leftMotor.setNeutralMode(NeutralMode.Coast);
        m_rightMotor.setNeutralMode(NeutralMode.Coast);
        m_leftFollowMotor.setNeutralMode(NeutralMode.Coast);
        m_rightFollowMotor.setNeutralMode(NeutralMode.Coast);
        // establish master
        m_leftFollowMotor.follow(m_leftMotor);
        m_rightFollowMotor.follow(m_rightMotor);
        // invert motor
        m_leftMotor.setInverted(true);
        m_rightMotor.setInverted(false);
        m_leftFollowMotor.setInverted(InvertType.FollowMaster);
        m_rightFollowMotor.setInverted(InvertType.FollowMaster);

    }

    public void arcadeDrive(double pow, double turn) {
        pow *= speedMultiplier;
        if (pow - pow0 >= aLimit) {
            pow = pow0 + aLimit;
        }

        pow0 = pow;
        m_leftMotor.set(ControlMode.PercentOutput, pow - turn);
        m_rightMotor.set(ControlMode.PercentOutput, pow + turn);
    }
    public void disable() {
        m_rightMotor.set(ControlMode.PercentOutput, 0);
        m_leftMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setSpeedMultiplier(double speed, boolean updateNT) {
        if (0 <= speed && speed <= 2) {
            speedMultiplier = speed;
        }
    }

    @Override
    public void periodic() {
    }
}
