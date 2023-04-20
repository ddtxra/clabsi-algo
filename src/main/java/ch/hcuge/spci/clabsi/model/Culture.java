package ch.hcuge.spci.clabsi.model;

import ch.hcuge.spci.clabsi.AlgoConstants;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

abstract class Culture {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected String patientId;
    protected ZonedDateTime stayBeginDate;
    protected ZonedDateTime laboSampleDate;
    protected String laboGermName;
    protected GermType laboCommensal;
    protected Map<String, String> specificInfo;

    public String getPatientId() {
        return patientId;
    }

    public ZonedDateTime getStayBeginDate() {
        return stayBeginDate;
    }

    public ZonedDateTime getLaboSampleDate() {
        return laboSampleDate;
    }

    public String getLaboGermName() {
        return laboGermName;
    }

    public String getStayBeginCalendarDayISO() {
        return formatter.format(stayBeginDate);
    }
    public String getLaboCalendarDayISO() {
        return formatter.format(laboSampleDate);
    }
    public GermType getLaboCommensal() {
        return laboCommensal;
    }

    public Map<String, String> getSpecificInfo() {
        return specificInfo;
    }

    protected Culture (String patientId, ZonedDateTime stayBeginDate, ZonedDateTime laboSampleDate, String laboGermName, GermType laboCommensal, Map<String, String> specificInfo) {
        this.patientId = patientId;
        this.stayBeginDate = stayBeginDate;
        this.laboSampleDate = laboSampleDate;
        this.laboGermName = laboGermName;
        this.laboCommensal = laboCommensal;
        this.specificInfo = specificInfo;
    }

    protected Culture (String patientId, ZonedDateTime stayBeginDate, ZonedDateTime laboSampleDate, String laboGermName, GermType laboCommensal) {
        this(patientId, stayBeginDate, laboSampleDate, laboGermName, laboCommensal, new HashMap<>());
    }


    private long getNumberOfCalendarDaysSinceAdmission(){
        return ChronoUnit.DAYS.between(this.stayBeginDate.toLocalDate(), this.laboSampleDate.toLocalDate());
    }

    private long getNumberOfDaysSinceAdmission(){
        return ChronoUnit.DAYS.between(this.stayBeginDate, this.laboSampleDate);
    }

    //FIXME should be at the level of the episode
    public boolean isNosocomial(){
        if(AlgoConstants.USE_CALENDAR_DAY_TO_COMPUTE_NOSOCOMIAL){
            return getNumberOfCalendarDaysSinceAdmission() >= 2;
        }else {
            return getNumberOfDaysSinceAdmission() >= 2;
        }
    }


}
