package com.example.demo.converter;

import com.example.demo.model.GymUserModel;
import org.springframework.stereotype.Service;
import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.FloatVector;

import java.util.List;

@Service
public class GymUserToDataFrameConverter {

    public static DataFrame convert(List<GymUserModel> gymUsers) {
        int size = gymUsers.size();
        int[] attendanceArray = new int[size];
        float[] bmiArray = new float[size];
        int[] ageArray = new int[size];
        float[] weightArray = new float[size];
        float[] heightArray = new float[size];

        for (int i = 0; i < size; i++) {
            GymUserModel gymUser = gymUsers.get(i);

            Integer attendance = gymUser.getAttendance();
            if (attendance != null) {
                attendanceArray[i] = attendance.intValue();
            } else {
                attendanceArray[i] = 0;
            }

            Float bmi = gymUser.getHeight()*gymUser.getWeight();
            if (bmi != null) {
                bmiArray[i] = bmi.floatValue();
            } else {
                bmiArray[i] = 0.0f;
            }

            Integer age = gymUser.getBirthDate().getYear();
            if (age != null) {
                ageArray[i] = age.intValue();
            } else {
                ageArray[i] = 0;
            }

            Float weight = gymUser.getWeight();
            if (weight != null) {
                weightArray[i] = weight.floatValue();
            } else {
                weightArray[i] = 0.0f;
            }

            Float height = gymUser.getHeight();
            if (height != null) {
                heightArray[i] = height.floatValue();
            } else {
                heightArray[i] = 0.0f;
            }
        }

        DataFrame data = DataFrame.of(
            IntVector.of("attendance", attendanceArray),
            FloatVector.of("bmi", bmiArray),
            IntVector.of("age", ageArray),
            FloatVector.of("weight", weightArray),
            FloatVector.of("height", heightArray)
        );

        return data;
    }
}
