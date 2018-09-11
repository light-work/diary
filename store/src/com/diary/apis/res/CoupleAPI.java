package com.diary.apis.res;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.res.CoupleBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/couple")
public class CoupleAPI extends BaseAPI {


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
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.list(start, limit, keyword);
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
    public Response effectList(@QueryParam("coupleId") Long coupleId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (coupleId == null) {
            errorBuilder.append("coupleId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.effectList(coupleId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/requireList")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response requireList(@QueryParam("coupleId") Long coupleId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (coupleId == null) {
            errorBuilder.append("coupleId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.requireList(coupleId);
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
            @FormParam("title") String title, @FormParam("price") Long price,
            @FormParam("gender") Integer gender, @FormParam("desc") String desc) {
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
        if (StringUtils.isBlank(desc)) {
            errorBuilder.append("desc was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.add(title, price, gender, desc);
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
                         @FormParam("title") String title, @FormParam("price") Long price,
                         @FormParam("gender") Integer gender, @FormParam("desc") String desc) {
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
        if (StringUtils.isBlank(desc)) {
            errorBuilder.append("desc was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.edit(id, title, price, gender, desc);
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
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.enable(id);
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
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.disable(id);
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
    public Response addEffect(@FormParam("coupleId") Long coupleId,
                              @FormParam("operation") String operation,
                              @FormParam("attrKey") String attrKey,
                              @FormParam("value") Long value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (coupleId == null) {
            errorBuilder.append("coupleId was null.");
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
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.addEffect(coupleId, operation, attrKey, value);
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
                              @FormParam("value") Long value) {
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
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.editEffect(id, operation, attrKey, value);
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
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.deleteEffect(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }



    @Path("/addRequire")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response addRequire(@FormParam("coupleId") Long coupleId,
                              @FormParam("attrKey") String attrKey,
                              @FormParam("value") Long value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (coupleId == null) {
            errorBuilder.append("coupleId was null.");
        }
        if (value == null) {
            errorBuilder.append("value was null.");
        }
        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.addRequire(coupleId, attrKey, value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/editRequire")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response editRequire(@FormParam("id") Long id,
                               @FormParam("attrKey") String attrKey,
                               @FormParam("value") Long value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }

        if (value == null) {
            errorBuilder.append("value was null.");
        }
        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.editRequire(id, attrKey, value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/deleteRequire")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response deleteRequire(@FormParam("id") Long id) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                CoupleBiz coupleBiz = hsfServiceFactory.consumer(CoupleBiz.class);
                if (coupleBiz != null) {
                    bizResult = coupleBiz.deleteRequire(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }
}
