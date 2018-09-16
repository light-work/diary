package com.diary.apis.res;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.res.CommonBiz;
import com.diary.providers.biz.res.CoupleBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/common")
public class CommonAPI extends BaseAPI {


    @Path("/getAttr/{gender}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response list(@PathParam("gender") Integer gender) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (gender == null) {
            errorBuilder.append("gender was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                CommonBiz commonBiz = hsfServiceFactory.consumer(CommonBiz.class);
                if (commonBiz != null) {
                    bizResult = commonBiz.getAttr(gender);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/getOperation")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOperation() {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (errorBuilder.length() == 0) {
            try {
                CommonBiz commonBiz = hsfServiceFactory.consumer(CommonBiz.class);
                if (commonBiz != null) {
                    bizResult = commonBiz.getOperation();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/getCompare")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getCompare() {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (errorBuilder.length() == 0) {
            try {
                CommonBiz commonBiz = hsfServiceFactory.consumer(CommonBiz.class);
                if (commonBiz != null) {
                    bizResult = commonBiz.getCompare();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

}
