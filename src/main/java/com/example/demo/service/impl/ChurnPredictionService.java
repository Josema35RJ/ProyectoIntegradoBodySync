package com.example.demo.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.GymUserToDataFrameConverter;
import com.example.demo.model.ChurnPredictionModel;
import com.example.demo.model.GymUserModel;
import com.example.demo.service.GymUserService;

import smile.data.DataFrame;

@Service("churnPredictionService")
public class ChurnPredictionService {

    private final GymUserService gymUserService;
    private final GymUserToDataFrameConverter gymUserToDataFrameConverter;
    private final ChurnPredictionModel churnPredictionModel;

    @Autowired
    public ChurnPredictionService(GymUserService gymUserService, GymUserToDataFrameConverter gymUserToDataFrameConverter, ChurnPredictionModel churnPredictionModel) {
        this.gymUserService = gymUserService;
        this.gymUserToDataFrameConverter = gymUserToDataFrameConverter;
        this.churnPredictionModel = churnPredictionModel;
    }

    public void updateChurnRiskForAllUsers() {
        List<GymUserModel> members = gymUserService.ListAllGymUsers();
        for (GymUserModel gymUser : members) {
            DataFrame data = gymUserToDataFrameConverter.convert(Collections.singletonList(gymUser));
            boolean churnRisk = churnPredictionModel.predict(data);
            gymUser.setChurn(churnRisk);
        }
    }

	public boolean predict(DataFrame data) {
		// TODO: Implementa la lógica de predicción aquí
		return false;
	}
}
