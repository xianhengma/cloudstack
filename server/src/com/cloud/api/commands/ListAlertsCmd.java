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

import com.cloud.alert.AlertVO;
import com.cloud.api.ApiConstants;
import com.cloud.api.BaseListCmd;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.AlertResponse;
import com.cloud.api.response.ListResponse;

@Implementation(method="searchForAlerts", description="Lists all alerts.")
public class ListAlertsCmd extends BaseListCmd {

    public static final Logger s_logger = Logger.getLogger(ListAlertsCmd.class.getName());

    private static final String s_name = "listalertsresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name=ApiConstants.TYPE, type=CommandType.STRING, description="list by alert type")
    private String type;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public String getType() {
        return type;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getName() {
        return s_name;
    }

    @Override @SuppressWarnings("unchecked")
    public ListResponse<AlertResponse> getResponse() {
        List<AlertVO> alertList = (List<AlertVO>)getResponseObject();

        ListResponse<AlertResponse> response = new ListResponse<AlertResponse>();
        List<AlertResponse> alertResponseList = new ArrayList<AlertResponse>();
        for (AlertVO alert : alertList) {
            AlertResponse alertResponse = new AlertResponse();
            alertResponse.setId(alert.getId());
            alertResponse.setAlertType(alert.getType());
            alertResponse.setDescription(alert.getSubject());
            alertResponse.setLastSent(alert.getLastSent());

            alertResponse.setResponseName("alert");
            alertResponseList.add(alertResponse);
        }

        response.setResponses(alertResponseList);
        response.setResponseName(getName());
        return response;
    }
}
