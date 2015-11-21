package com.test;

import com.test.exception.APIErrorMessage;
import com.test.exception.APIException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by root on 20/11/15.
 */
@Path("image")
public class ImageResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem( @FormDataParam("itemid") String itemId,
                             @FormDataParam ("file") InputStream uploadedInputStream,
                             @FormDataParam ("file") FormDataContentDisposition fileDetail
                             )
    {
        File myDir = new File("/tmp/ImageFolder");
        myDir.mkdir();
        File imageFile = new File("/tmp/ImageFolder/"+fileDetail.getFileName()+"itemid_"+itemId) ;
        String outputJson = "";
        try {
            imageFile.createNewFile();
            saveToFile(uploadedInputStream,imageFile);
            ObjectMapper mapper = new ObjectMapper();
            outputJson = mapper.writeValueAsString("Image added");
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException(Response.Status.INTERNAL_SERVER_ERROR, new APIErrorMessage("Internal Server Error").toJson());

        }
        return Response.ok(outputJson, MediaType.APPLICATION_JSON).build();
    }
    private void saveToFile (InputStream uploadStream, File uploadFile) throws Exception
    {

            OutputStream out = new FileOutputStream(uploadFile);

            int read = 0;
            byte[] bytes = new byte[1024];

            read = uploadStream.read (bytes);
            while (read != -1)
            {
                out.write (bytes, 0, read);
                read = uploadStream.read (bytes);
            }
            out.flush();
            out.close();

    }
}
