package org.openmrs.module.kenyaemrorderentry.page.controller.orders;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemrorderentry.api.service.KenyaemrOrdersService;
import org.openmrs.module.kenyaemrorderentry.manifest.LabManifest;
import org.openmrs.module.kenyaemrorderentry.manifest.LabManifestOrder;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

import java.util.ArrayList;
import java.util.List;

@AppPage("kenyaemr.labmanifest")
public class LabOrdersManifestHomePageController {

    KenyaemrOrdersService kenyaemrOrdersService = Context.getService(KenyaemrOrdersService.class);

    public void get(@SpringBean KenyaUiUtils kenyaUi,
                    UiUtils ui, PageModel model) {
        List<LabManifest> allManifests = Context.getService(KenyaemrOrdersService.class).getLabOrderManifest("Draft");
        List<SimpleObject> manifestList1 = new ArrayList<SimpleObject>();
        for (LabManifest manifest : allManifests) {

            List<LabManifestOrder> allSamples = kenyaemrOrdersService.getLabManifestOrderByManifest(manifest);

            String manifestType = "";
            if (manifest.getManifestType() != null && manifest.getManifestType().intValue() == 1) {
                manifestType = "EID";
            } else if (manifest.getManifestType() != null && manifest.getManifestType().intValue() == 2) {
                manifestType = "VL";
            }
            SimpleObject m = SimpleObject.create(
                    "id", manifest.getId(),
                    "startDate", manifest.getStartDate() != null ? ui.formatDatePretty(manifest.getStartDate()) : "",
                    "endDate", manifest.getEndDate() != null ? ui.formatDatePretty(manifest.getEndDate()) : "",
                    "manifestType", manifestType,
                    "dispatchDate", manifest.getDispatchDate() != null ? ui.formatDatePretty(manifest.getDispatchDate()) : "",
                    "courier", StringUtils.capitalize(manifest.getCourier() != null ? manifest.getCourier().toLowerCase() : ""),
                    "courierOfficer", StringUtils.capitalize(manifest.getCourierOfficer() != null ? manifest.getCourierOfficer().toLowerCase() : ""),
                    "status", manifest.getStatus(),
                    "facilityEmail", manifest.getFacilityEmail(),
                    "identifier", manifest.getIdentifier() != null ? manifest.getIdentifier() : "",
                    "facilityPhoneContact", manifest.getFacilityPhoneContact(),
                    "clinicianPhoneContact", manifest.getClinicianPhoneContact(),
                    "clinicianName", StringUtils.capitalize(manifest.getClinicianName() != null ? manifest.getClinicianName().toLowerCase() : ""),
                    "labPocPhoneNumber", manifest.getLabPocPhoneNumber()

            );

            SimpleObject o1 = SimpleObject.create(
                    "manifest", m,
                    "collectNewSample", 0,
                    "incompleteSample", 0,
                    "manualUpdates", 0,
                    "recordsNotFound", 0,
                    "totalSamples", allSamples.size(),
                    "missingPhysicalSample", 0);
            manifestList1.add(o1);
        }
        model.put("manifestList", ui.toJson(manifestList1));
        model.put("manifestListSize", ui.toJson(manifestList1.size()));
    }

}