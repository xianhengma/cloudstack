// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package org.apache.cloudstack.acl.entity;

import java.util.List;

import org.apache.log4j.Logger;

import org.apache.cloudstack.acl.QuerySelector;

import com.cloud.user.Account;
import com.cloud.utils.component.AdapterBase;

public class RoleBasedEntityQuerySelector extends AdapterBase implements QuerySelector {

    private static final Logger s_logger = Logger.getLogger(RoleBasedEntityQuerySelector.class.getName());

    @Override
    public List<Long> getAuthorizedDomains(Account caller, String action) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Long> getAuthorizedAccounts(Account caller, String action) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Long> getAuthorizedResources(Account caller, String action) {
        // TODO Auto-generated method stub
        return null;
    }


}