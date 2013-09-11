/*******************************************************************************
 * Copyright 2012 Technology Blueprint Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package uk.co.techblue.docusign.client.resources;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.james.mime4j.field.FieldName;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import uk.co.techblue.docusign.client.Resource;
import uk.co.techblue.docusign.client.dto.CustomField;
import uk.co.techblue.docusign.client.dto.DocumentFile;
import uk.co.techblue.docusign.client.dto.Envelope;
import uk.co.techblue.docusign.client.dto.EnvelopeDetailInfo;
import uk.co.techblue.docusign.client.dto.EnvelopeDocumentInfo;
import uk.co.techblue.docusign.client.dto.EnvelopeNotificationInfo;
import uk.co.techblue.docusign.client.dto.EnvelopeStatusQueryForm;
import uk.co.techblue.docusign.client.dto.EnvelopeStatusResponse;
import uk.co.techblue.docusign.client.dto.StatusChangeRequest;
import uk.co.techblue.docusign.client.dto.recipients.RecipientStatusCollection;
import uk.co.techblue.docusign.client.dto.user.DocuSignCredentials;
import uk.co.techblue.docusign.client.utils.DocuSignConstants;

@Path(DocuSignConstants.RESOURCE_CONTEXT_PATH)
public interface EnvelopeResource extends Resource {

    @PUT
    @Path("envelopes/{envelopeId}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public ClientResponse<String> changeStatus(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId,
            StatusChangeRequest statusChangeRequest);

    @PUT
    @Path("envelopes/{envelopeId}/documents/{documentId}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public ClientResponse<String> addDocumentToDraftEnvelope(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @HeaderParam(FieldName.CONTENT_DISPOSITION) String contentDisposition,
            @PathParam("envelopeId") String envelopeId,
            @PathParam("documentId") String documentId, File docFile);

    @GET
    @Path("envelopes/{envelopeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse<EnvelopeDetailInfo> getEnvelope(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("envelopes")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ClientResponse<Map<String, String>> saveToDrafts(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            MultipartFormDataOutput formDataOutput);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("envelopes")
    @Consumes(MediaType.APPLICATION_JSON)
    public ClientResponse<Map<String, String>> saveToDrafts(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            Envelope envelope);

    @GET
    @Path("envelopes")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse<EnvelopeStatusResponse> getEnvelopeStatus(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @Form EnvelopeStatusQueryForm statusQueryForm);

    @GET
    @Path("envelopes/{envelopeId}/documents/certificate")
    public ClientResponse<DocumentFile> getCertificate(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId,
            @QueryParam("watermark") Boolean watermark,
            @QueryParam("certificate") Boolean certificate);

    @GET
    @Path("envelopes/{envelopeId}/documents/combined")
    public ClientResponse<DocumentFile> getDocumentsCombined(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId);

    @GET
    @Path("envelopes/{envelopeId}/documents")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse<EnvelopeDocumentInfo> getDocumentsInfo(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId);

    @GET
    @Path("/envelopes/{envelopeId}/documents/{documentId}")
    public ClientResponse<DocumentFile> getDocument(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId,
            @PathParam("documentId") String documentId);


    @GET
    @Path("/envelopes/{envelopeId}/custom_fields")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse<Map<String,List<CustomField>>> getCustomFields(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId);

    @GET
    @Path("/envelopes/{envelopeId}/notification")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse<EnvelopeNotificationInfo> getNotificationInfo(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId);

    @GET
    @Path("/envelopes/{envelopeId}/recipients")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientResponse<RecipientStatusCollection> getRecipientStatus(
            @HeaderParam(DocuSignConstants.HEADER_PARAM_AUTHENTICATION) DocuSignCredentials credentials,
            @PathParam("envelopeId") String envelopeId,
            @QueryParam("include_tabs") Boolean includeTabs,
            @QueryParam("include_extended") Boolean includeExtended);

}
