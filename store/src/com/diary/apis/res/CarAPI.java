package com.diary.apis.res;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.res.CarBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/car")
public class CarAPI extends BaseAPI {


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
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.list(start, limit, keyword);
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
    public Response effectList(@QueryParam("carId") Long carId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (carId == null) {
            errorBuilder.append("carId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.effectList(carId);
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
            @FormParam("title") String title, @FormParam("buyPrice") Integer buyPrice,
            @FormParam("sellPrice") Integer sellPrice,
            @FormParam("offsetBuy") Integer offsetBuy,@FormParam("offsetSell") Integer offsetSell,
            @FormParam("remarks") String remarks) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (StringUtils.isBlank(title)) {
            errorBuilder.append("title was null.");
        }
        if (buyPrice == null) {
            errorBuilder.append("buyPrice was null.");
        }
        if (sellPrice == null) {
            errorBuilder.append("sellPrice was null.");
        }
        if (StringUtils.isBlank(remarks)) {
            errorBuilder.append("remarks was null.");
        }

        if (offsetBuy == null) {
            errorBuilder.append("offsetBuy was null.");
        }
        if (offsetSell == null) {
            errorBuilder.append("offsetSell was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.add(title, buyPrice, sellPrice,offsetBuy,offsetSell, remarks);
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
                         @FormParam("title") String title, @FormParam("buyPrice") Integer buyPrice,
                         @FormParam("sellPrice") Integer sellPrice,
                         @FormParam("offsetBuy") Integer offsetBuy,@FormParam("offsetSell") Integer offsetSell,
                         @FormParam("remarks") String remarks) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (StringUtils.isBlank(title)) {
            errorBuilder.append("title was null.");
        }
        if (buyPrice == null) {
            errorBuilder.append("buyPrice was null.");
        }
        if (sellPrice == null) {
            errorBuilder.append("sellPrice was null.");
        }
        if (offsetBuy == null) {
            errorBuilder.append("offsetBuy was null.");
        }
        if (offsetSell == null) {
            errorBuilder.append("offsetSell was null.");
        }
        if (StringUtils.isBlank(remarks)) {
            errorBuilder.append("remarks was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.edit(id, title, buyPrice, sellPrice, offsetBuy,offsetSell,remarks);
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
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.enable(id);
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
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.disable(id);
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
    public Response addEffect(@FormParam("carId") Long carId,
                              @FormParam("operation") String operation,
                              @FormParam("attrKey") String attrKey,
                              @FormParam("value") Integer value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (carId == null) {
            errorBuilder.append("carId was null.");
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
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.addEffect(carId, operation, attrKey, value);
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
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.editEffect(id, operation, attrKey, value);
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
                CarBiz carBiz = hsfServiceFactory.consumer(CarBiz.class);
                if (carBiz != null) {
                    bizResult = carBiz.deleteEffect(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }



}
