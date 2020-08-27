package de.samply.share.client.fhir;

import ca.uhn.fhir.context.ConfigurationException;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import org.hl7.fhir.r4.model.AuditEvent;
import org.hl7.fhir.r4.model.Bundle;

public class FHIRResource {
    private FhirContext ctx = FhirContext.forR4();

    public Bundle convertToBundleResource(String bundleString, String mediaType) throws ConfigurationException, DataFormatException {
        if (mediaType.equals("json")) {
            return (Bundle) ctx.newJsonParser().parseResource(bundleString);
        } else {
            return (Bundle) ctx.newXmlParser().parseResource(bundleString);
        }
    }

    public String convertBundleToXml(Bundle bundle){
        return ctx.newXmlParser().encodeResourceToString(bundle);
    }

    public String convertBundleToJson(Bundle bundle){
        return ctx.newJsonParser().encodeResourceToString(bundle);
    }


    public String convertAuditEventToJson(AuditEvent auditEvent){
        return ctx.newJsonParser().encodeResourceToString(auditEvent);
    }

}
