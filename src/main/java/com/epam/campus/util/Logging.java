package com.epam.campus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

public class Logging {
    public static  final Logger logger = LoggerFactory.getLogger(Logging.class);
    public void logging(){
        LocalDate localDate = LocalDate.now();
        logger.info("Date: {}",localDate);
    }
}
