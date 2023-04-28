package ch.hcuge.spci.bsi.impl.hugv2023.model;

import ch.hcuge.spci.bsi.Culture;
import ch.hcuge.spci.bsi.constants.GermType;
import ch.hcuge.spci.bsi.constants.GlobalParameters;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class PositiveHemoCultureHUGv2023 implements Culture {

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

    @Override
    public GermType getGermType() {
        return this.laboCommensal;
    }

    @Override
    public boolean isLabGermCommensal() {
        return (this.laboCommensal.equals(GermType.COMMENSAL));
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

    public PositiveHemoCultureHUGv2023(String patientId, ZonedDateTime stayBeginDate, ZonedDateTime laboSampleDate, String laboGermName, GermType laboCommensal, Map<String, String> specificInfo) {
        this.patientId = patientId;
        this.stayBeginDate = stayBeginDate;
        this.laboSampleDate = laboSampleDate;
        this.laboGermName = laboGermName;
        this.laboCommensal = laboCommensal;
        this.specificInfo = specificInfo;
    }

    public PositiveHemoCultureHUGv2023(String patientId, ZonedDateTime stayBeginDate, ZonedDateTime laboSampleDate, String laboGermName, GermType laboCommensal) {
        this(patientId, stayBeginDate, laboSampleDate, laboGermName, laboCommensal, new HashMap<>());
    }

    private long getNumberOfCalendarDaysSinceAdmission() {
        return ChronoUnit.DAYS.between(this.stayBeginDate.toLocalDate(), this.laboSampleDate.toLocalDate());
    }

    private long getNumberOfDaysSinceAdmission() {
        return ChronoUnit.DAYS.between(this.stayBeginDate, this.laboSampleDate);
    }

    //FIXME should be at the level of the episode
    public boolean isNosocomial() {
        if (GlobalParameters.USE_CALENDAR_DAY_TO_COMPUTE_NOSOCOMIAL) {
            return getNumberOfCalendarDaysSinceAdmission() >= 2;
        } else {
            return getNumberOfDaysSinceAdmission() >= 2;
        }
    }


}
