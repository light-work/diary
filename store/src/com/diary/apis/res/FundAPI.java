package com.diary.apis.res;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.res.FundBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/fund")
public class FundAPI extends BaseAPI {


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
                FundBiz fundBiz = hsfServiceFactory.consumer(FundBiz.class);
                if (fundBiz != null) {
                    bizResult = fundBiz.list(start, limit, keyword);
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
            @FormParam("title") String title, @FormParam("minNum") Double minNum,
            @FormParam("maxNum") Double maxNum,@FormParam("probability") Double probability, @FormParam("remarks") String remarks) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (StringUtils.isBlank(title)) {
            errorBuilder.append("title was null.");
        }
        if (minNum == null) {
            errorBuilder.append("minNum was null.");
        }
        if (maxNum == null) {
            errorBuilder.append("maxNum was null.");
        }
        if (probability == null) {
            errorBuilder.append("probability was null.");
        }
        if (StringUtils.isBlank(remarks)) {
            errorBuilder.append("remarks was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                FundBiz fundBiz = hsfServiceFactory.consumer(FundBiz.class);
                if (fundBiz != null) {
                    bizResult = fundBiz.add(title, minNum,maxNum,probability, remarks);
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
                         @FormParam("title") String title, @FormParam("minNum") Double minNum,
                         @FormParam("maxNum") Double maxNum,@FormParam("probability") Double probability, @FormParam("remarks") String remarks) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (id == null) {
            errorBuilder.append("id was null.");
        }
        if (StringUtils.isBlank(title)) {
            errorBuilder.append("title was null.");
        }
        if (minNum == null) {
            errorBuilder.append("minNum was null.");
        }
        if (maxNum == null) {
            errorBuilder.append("maxNum was null.");
        }
        if (probability == null) {
            errorBuilder.append("probability was null.");
        }
        if (StringUtils.isBlank(remarks)) {
            errorBuilder.append("remarks was null.");
        }

        if (errorBuilder.length() == 0) {
            try {
                FundBiz fundBiz = hsfServiceFactory.consumer(FundBiz.class);
                if (fundBiz != null) {
                    bizResult = fundBiz.edit(id, title, minNum, maxNum,probability, remarks);
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
                FundBiz fundBiz = hsfServiceFactory.consumer(FundBiz.class);
                if (fundBiz != null) {
                    bizResult = fundBiz.enable(id);
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
                FundBiz fundBiz = hsfServiceFactory.consumer(FundBiz.class);
                if (fundBiz != null) {
                    bizResult = fundBiz.disable(id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }



}
