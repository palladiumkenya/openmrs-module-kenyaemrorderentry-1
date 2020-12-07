package org.openmrs.module.kenyaemrorderentry.page.controller.orders;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.RegimenMappingUtils;
import org.openmrs.module.kenyaemrorderentry.api.itext.LabManifestReport;
import org.openmrs.module.kenyaemrorderentry.api.service.KenyaemrOrdersService;
import org.openmrs.module.kenyaemrorderentry.labDataExchange.LabOrderDataExchange;
import org.openmrs.module.kenyaemrorderentry.manifest.LabManifest;
import org.openmrs.module.kenyaemrorderentry.manifest.LabManifestOrder;
import org.openmrs.module.kenyaemrorderentry.util.Utils;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@AppPage("kenyaemr.labmanifest")
public class ManifestOrdersHomePageController {

    public void get(@RequestParam(value = "manifest") LabManifest manifest, @SpringBean KenyaUiUtils kenyaUi,
                    UiUtils ui, PageModel model) {

        List<LabManifestOrder> allOrdersForManifest = Context.getService(KenyaemrOrdersService.class).getLabManifestOrderByManifest(manifest);
        PatientIdentifierType pat = Utils.getUniquePatientNumberIdentifierType();
        LabOrderDataExchange e = new LabOrderDataExchange();
        model.put("manifest", manifest);
        model.put("manifestOrders", allOrdersForManifest);
        model.put("eligibleOrders", e.getActiveViralLoadOrdersNotInManifest(null,manifest.getStartDate(),manifest.getEndDate()));
        model.put("cccNumberType", pat.getPatientIdentifierTypeId());
    }

}