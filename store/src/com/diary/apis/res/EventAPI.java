package com.diary.apis.res;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.res.EventBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/event")
public class EventAPI extends BaseAPI {

    @Path("/edit")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response edit(@FormParam("id") Long id,
                             @FormParam("content") String content) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (StringUtils.isBlank(content)) {
            errorBuilder.append("content was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.editEvent(id,content);
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
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.enable(id);
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
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.disable(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/addResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response addResult(@FormParam("eventId") Long eventId,
                       @FormParam("resultText") String resultText, @FormParam("content")String content) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (eventId == null) {
            errorBuilder.append("eventId was null.");
        }

        if (StringUtils.isBlank(resultText)) {
            errorBuilder.append("resultText was null.");
        }

        if (StringUtils.isBlank(content)) {
            errorBuilder.append("content was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.addResult(eventId,resultText,content);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/editResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response editResult(@FormParam("id") Long id,
                              @FormParam("resultText") String resultText, @FormParam("content")String content) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("eventId was null.");
        }

        if (StringUtils.isBlank(resultText)) {
            errorBuilder.append("resultText was null.");
        }

        if (StringUtils.isBlank(content)) {
            errorBuilder.append("content was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.editResult(id,resultText,content);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/enableResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response enableResult(@FormParam("resultId") Long resultId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.enableResult(resultId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/disableResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response disableResult(@FormParam("resultId") Long resultId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.disableResult(resultId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/upResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response upResult(@FormParam("resultId") Long resultId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.upResult(resultId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/downResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response downResult(@FormParam("resultId") Long resultId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.downResult(resultId);
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
    public Response addEffect(@FormParam("resultId") Long resultId, @FormParam("operation") String operation,
                              @FormParam("attrKey") String attrKey, @FormParam("value") Integer value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }

        if (StringUtils.isBlank(operation)) {
            errorBuilder.append("operation was null.");
        }

        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }
        if (value==null) {
            errorBuilder.append("value was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.addEffect(resultId, operation, attrKey, value);
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
    public Response editEffect(@FormParam("id") Long id, @FormParam("operation") String operation,
                              @FormParam("attrKey") String attrKey, @FormParam("value") Integer value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }

        if (StringUtils.isBlank(operation)) {
            errorBuilder.append("operation was null.");
        }

        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }
        if (value==null) {
            errorBuilder.append("value was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.editEffect(id, operation, attrKey, value);
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
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.deleteEffect(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/setRequire")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response setRequire(@FormParam("resultId") Long resultId, @FormParam("compare") String compare,
                               @FormParam("attrKey") String attrKey, @FormParam("value") Integer value) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }

        if (StringUtils.isBlank(compare)) {
            errorBuilder.append("compare was null.");
        }

        if (StringUtils.isBlank(attrKey)) {
            errorBuilder.append("attrKey was null.");
        }
        if (value==null) {
            errorBuilder.append("value was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.setRequire(resultId, compare, attrKey, value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/clearRequire")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response clearRequire(@FormParam("resultId") Long resultId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (resultId == null) {
            errorBuilder.append("resultId was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                EventBiz eventBiz = hsfServiceFactory.consumer(EventBiz.class);
                if (eventBiz != null) {
                    bizResult = eventBiz.clearRequire(resultId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }
}

