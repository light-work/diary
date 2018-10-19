package com.diary.apis.app;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.app.UserEventBiz;
import com.diary.providers.biz.res.CarBiz;
import com.diary.providers.biz.res.EventBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/userEvent")
public class EventAPI extends BaseAPI {


    @Path("/findEvent")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findEvent(@QueryParam("userId") Long userId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserEventBiz userEventBiz = hsfServiceFactory.consumer(UserEventBiz.class);
                if (userEventBiz != null) {
                    bizResult = userEventBiz.findEvent(userId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/load")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response load(@QueryParam("userId") Long userId,
                         @QueryParam("eventId") Long eventId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (eventId == null) {
            errorBuilder.append("eventId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserEventBiz userEventBiz = hsfServiceFactory.consumer(UserEventBiz.class);
                if (userEventBiz != null) {
                    bizResult = userEventBiz.loadEvent(userId, eventId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }



    @Path("/applyResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response applyResult(@FormParam("userId") Long userId,
                            @FormParam("resultId") Long resultId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserEventBiz userEventBiz = hsfServiceFactory.consumer(UserEventBiz.class);
                if (userEventBiz != null) {
                    bizResult = userEventBiz.applyResult(userId, resultId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

}
