package com.diary.apis.res;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.res.PlanBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/plan")
public class PlanAPI extends BaseAPI {


    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response list(@QueryParam("start") Integer start,
                         @QueryParam("limit") Integer limit,
                         @QueryParam("keyword") String keyword) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (start == null) {
            errorBuilder.append("start was null.");
        }
        if (limit == null) {
            errorBuilder.append("limit was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.list(start, limit, keyword);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/effectList")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response effectList(@QueryParam("planId") Long planId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (planId == null) {
            errorBuilder.append("planId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.effectList(planId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response add(
            @FormParam("title") String title, @FormParam("price") Integer price,
            @FormParam("gender") Integer gender, @FormParam("remarks") String remarks) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (StringUtils.isBlank(title)) {
            errorBuilder.append("title was null.");
        }
        if (price == null) {
            errorBuilder.append("price was null.");
        }
        if (gender == null) {
            errorBuilder.append("gender was null.");
        }
        if (StringUtils.isBlank(remarks)) {
            errorBuilder.append("remarks was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.add(title, price, gender, remarks);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/edit")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response edit(@FormParam("id") Long id,
                         @FormParam("title") String title, @FormParam("price") Integer price,
                         @FormParam("gender") Integer gender, @FormParam("desc") String remarks) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (StringUtils.isBlank(title)) {
            errorBuilder.append("title was null.");
        }
        if (price == null) {
            errorBuilder.append("price was null.");
        }
        if (gender == null) {
            errorBuilder.append("gender was null.");
        }
        if (StringUtils.isBlank(remarks)) {
            errorBuilder.append("remarks was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.edit(id, title, price, gender, remarks);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/enable")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response enable(@FormParam("id") Long id) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.enable(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/disable")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response disable(@FormParam("id") Long id) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.disable(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/addEffect")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response addEffect(@FormParam("planId") Long planId,
                              @FormParam("operation") String operation,
                              @FormParam("attrKey") String attrKey,
                              @FormParam("value") Integer value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (planId == null) {
            errorBuilder.append("planId was null.");
        }
        if (StringUtils.isBlank(operation)) {
            errorBuilder.append("operation was null.");
        }
        if (value == null) {
            errorBuilder.append("value was null.");
        }
        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.addEffect(planId, operation, attrKey, value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/editEffect")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response editEffect(@FormParam("id") Long id,
                              @FormParam("operation") String operation,
                              @FormParam("attrKey") String attrKey,
                              @FormParam("value") Integer value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (StringUtils.isBlank(operation)) {
            errorBuilder.append("operation was null.");
        }
        if (value == null) {
            errorBuilder.append("value was null.");
        }
        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.editEffect(id, operation, attrKey, value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/deleteEffect")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response deleteEffect(@FormParam("id") Long id) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                PlanBiz planBiz = hsfServiceFactory.consumer(PlanBiz.class);
                if (planBiz != null) {
                    bizResult = planBiz.deleteEffect(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }



}
