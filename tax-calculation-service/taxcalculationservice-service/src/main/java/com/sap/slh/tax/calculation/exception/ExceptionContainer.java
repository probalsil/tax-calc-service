package com.sap.slh.tax.calculation.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionContainer extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private List<ApplicationException> innerExeptions = new ArrayList<ApplicationException>();

    public void add(ApplicationException e) {
        innerExeptions.add(e);
    }

    public List<ApplicationException> getExceptions() {
        return innerExeptions;
    }
}
