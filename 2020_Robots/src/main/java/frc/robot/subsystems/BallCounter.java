/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//used to count the balls stored in the robot as they come in
public class BallCounter extends SubsystemBase {
    private int MAX_BALLS = 5;
    private int SAMPLES = 10;

    private int balls;
    private int sampledBalls;
    private float sampleAverage;

    public BallCounter(){
        this(0);
    }

    public BallCounter(int balls){
        this.balls = balls;
        sampledBalls = 0;
        sampleAverage = 0;
    }

    //returns true when full
    public boolean update(){
        //check the breaker
        if (sampledBalls < SAMPLES){
            
        }

        //check the maximum ball ammount needed
        if (balls == MAX_BALLS){
            return true;
        }

        //default return if the ball count is not full
        return false;
    }

    public int getBalls(){
        return balls;
    }

    public void reset(){
        balls = 0;
    }
}