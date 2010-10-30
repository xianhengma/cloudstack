/**
 *  Copyright (C) 2010 Cloud.com, Inc.  All rights reserved.
 * 
 * This software is licensed under the GNU General Public License v3 or later.
 * 
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.cloud.api.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.ApiDBUtils;
import com.cloud.api.BaseListCmd;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.DomainRouterResponse;
import com.cloud.api.response.ListResponse;
import com.cloud.async.AsyncJobVO;
import com.cloud.user.Account;
import com.cloud.vm.DomainRouterVO;

@Implementation(method="searchForRouters", description="List routers.")
public class ListRoutersCmd extends BaseListCmd {
    public static final Logger s_logger = Logger.getLogger(ListRoutersCmd.class.getName());

    private static final String s_name = "listroutersresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name=ApiConstants.ACCOUNT, type=CommandType.STRING, description="the name of the account associated with the router. Must be used with the domainId parameter.")
    private String accountName;

    @Parameter(name=ApiConstants.DOMAIN_ID, type=CommandType.LONG, description="the domain ID associated with the router. If used with the account parameter, lists all routers associated with an account in the specified domain.")
    private Long domainId;

    @Parameter(name=ApiConstants.HOST_ID, type=CommandType.LONG, description="the host ID of the router")
    private Long hostId;

    @Parameter(name=ApiConstants.NAME, type=CommandType.STRING, description="the name of the router")
    private String routerName;

    @Parameter(name=ApiConstants.POD_ID, type=CommandType.LONG, description="the Pod ID of the router")
    private Long podId;

    @Parameter(name=ApiConstants.STATE, type=CommandType.STRING, description="the state of the router")
    private String state;

    @Parameter(name=ApiConstants.ZONE_ID, type=CommandType.LONG, description="the Zone ID of the router")
    private Long zoneId;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public String getAccountName() {
        return accountName;
    }

    public Long getDomainId() {
        return domainId;
    }

    public Long getHostId() {
        return hostId;
    }

    public String getRouterName() {
        return routerName;
    }

    public Long getPodId() {
        return podId;
    }

    public String getState() {
        return state;
    }

    public Long getZoneId() {
        return zoneId;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getName() {
        return s_name;
    }

    @Override @SuppressWarnings("unchecked")
    public ListResponse<DomainRouterResponse> getResponse() {
        List<DomainRouterVO> routers = (List<DomainRouterVO>)getResponseObject();

        ListResponse<DomainRouterResponse> response = new ListResponse<DomainRouterResponse>();
        List<DomainRouterResponse> routerResponses = new ArrayList<DomainRouterResponse>();
        for (DomainRouterVO router : routers) {
            DomainRouterResponse routerResponse = new DomainRouterResponse();
            routerResponse.setId(router.getId());

            AsyncJobVO asyncJob = ApiDBUtils.findInstancePendingAsyncJob("domain_router", router.getId());
            if (asyncJob != null) {
                routerResponse.setJobId(asyncJob.getId());
                routerResponse.setJobStatus(asyncJob.getStatus());
            } 

            routerResponse.setZoneId(router.getDataCenterId());
            routerResponse.setZoneName(ApiDBUtils.findZoneById(router.getDataCenterId()).getName());
            routerResponse.setDns1(router.getDns1());
            routerResponse.setDns2(router.getDns2());
            routerResponse.setNetworkDomain(router.getDomain());
            routerResponse.setGateway(router.getGateway());
            routerResponse.setName(router.getName());
            routerResponse.setPodId(router.getPodId());

            if (router.getHostId() != null) {
                routerResponse.setHostId(router.getHostId());
                routerResponse.setHostName(ApiDBUtils.findHostById(router.getHostId()).getName());
            } 

            routerResponse.setPrivateIp(router.getPrivateIpAddress());
            routerResponse.setPrivateMacAddress(router.getPrivateMacAddress());
            routerResponse.setPrivateNetmask(router.getPrivateNetmask());
            routerResponse.setPublicIp(router.getPublicIpAddress());
            routerResponse.setPublicMacAddress(router.getPublicMacAddress());
            routerResponse.setPublicNetmask(router.getPublicNetmask());
            routerResponse.setGuestIpAddress(router.getGuestIpAddress());
            routerResponse.setGuestMacAddress(router.getGuestMacAddress());
            routerResponse.setGuestNetmask(router.getGuestNetmask());
            routerResponse.setTemplateId(router.getTemplateId());
            routerResponse.setCreated(router.getCreated());
            routerResponse.setState(router.getState());

            Account accountTemp = ApiDBUtils.findAccountById(router.getAccountId());
            if (accountTemp != null) {
                routerResponse.setAccountName(accountTemp.getAccountName());
                routerResponse.setDomainId(accountTemp.getDomainId());
                routerResponse.setDomainName(ApiDBUtils.findDomainById(accountTemp.getDomainId()).getName());
            }

            routerResponse.setResponseName("router");
            routerResponses.add(routerResponse);
        }

        response.setResponses(routerResponses);
        response.setResponseName(getName());
        return response;
    }
}
