package com.example.demo.model;

import smile.classification.LogisticRegression;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import org.springframework.stereotype.Service;
import smile.classification.LogisticRegression;
import smile.data.DataFrame;
import smile.data.formula.Formula;

@Service
public class ChurnPredictionModel {

    private LogisticRegression model;

    public void train(DataFrame data) {
        Formula formula = Formula.lhs("churn"); // Asume que "churn" es un campo booleano que indica si el miembro se dio de baja
        model = LogisticRegression.fit(formula, data);
    }

    public boolean predict(DataFrame data) {
        double[] row = data.get(0).toArray();
        return model.predict(row) == 1;
    }
}
