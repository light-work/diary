package com.diary.apis.app;

import com.diary.common.BaseAPI;
import com.diary.providers.biz.app.UserBiz;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gbcp on 16/8/8.
 */
@Path("/user")
public class UserAPI extends BaseAPI {


    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("code") String code) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (StringUtils.isBlank(code)) {
            errorBuilder.append("code was null.");
        }


        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.login(code);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/start")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response start(@FormParam("userId") Long userId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.start(userId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/resData/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response resData(@PathParam("userId") Long userId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.resData(userId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/refresh/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response refresh(@PathParam("userId") Long userId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.refresh(userId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/applyJob")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response applyJob(@FormParam("userId") Long userId,
                             @FormParam("jobId") Long jobId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (jobId == null) {
            errorBuilder.append("jobId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.applyJob(userId, jobId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/applyPlan")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response applyPlan(@FormParam("userId") Long userId,
                              @FormParam("planId") Long planId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (planId == null) {
            errorBuilder.append("planId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.applyPlan(userId, planId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/nextDay")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response nextDay(@FormParam("userId") Long userId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.nextDay(userId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/buyCar")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response buyCar(@FormParam("userId") Long userId,
                           @FormParam("carId") Long carId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (carId == null) {
            errorBuilder.append("carId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.buyCar(userId, carId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/sellCar")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellCar(@FormParam("userId") Long userId,
                            @FormParam("carId") Long carId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (carId == null) {
            errorBuilder.append("carId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.sellCar(userId, carId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/buyHouse")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response buyHouse(@FormParam("userId") Long userId,
                           @FormParam("houseId") Long houseId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (houseId == null) {
            errorBuilder.append("houseId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.buyHouse(userId, houseId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/sellHouse")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellHouse(@FormParam("userId") Long userId,
                            @FormParam("houseId") Long houseId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (houseId == null) {
            errorBuilder.append("houseId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.sellHouse(userId, houseId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/buyClothes")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response buyClothes(@FormParam("userId") Long userId,
                             @FormParam("clothesId") Long clothesId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (clothesId == null) {
            errorBuilder.append("clothesId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.buyClothes(userId, clothesId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/sellClothes")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellClothes(@FormParam("userId") Long userId,
                              @FormParam("clothesId") Long clothesId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (clothesId == null) {
            errorBuilder.append("clothesId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.sellClothes(userId, clothesId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }


    @Path("/buyLuxury")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response buyLuxury(@FormParam("userId") Long userId,
                               @FormParam("clothesId") Long luxuryId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (luxuryId == null) {
            errorBuilder.append("luxuryId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.buyLuxury(userId, luxuryId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

    @Path("/sellLuxury")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellLuxury(@FormParam("userId") Long userId,
                                @FormParam("luxuryId") Long luxuryId) {
        JSONObject result = new JSONObject();
        String bizResult = null;
        StringBuilder errorBuilder = new StringBuilder();
        if (userId == null) {
            errorBuilder.append("userId was null.");
        }
        if (luxuryId == null) {
            errorBuilder.append("luxuryId was null.");
        }
        if (errorBuilder.length() == 0) {
            try {
                UserBiz userBiz = hsfServiceFactory.consumer(UserBiz.class);
                if (userBiz != null) {
                    bizResult = userBiz.sellLuxury(userId, luxuryId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        result = buildResult(result, errorBuilder, bizResult);
        return Response.ok().entity(result.toString()).build();
    }

}
