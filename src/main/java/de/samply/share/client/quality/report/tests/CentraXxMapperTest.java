package de.samply.share.client.quality.report.tests;/*
* Copyright (C) 2017 Medizinische Informatik in der Translationalen Onkologie,
* Deutsches Krebsforschungszentrum in Heidelberg
*
* This program is free software; you can redistribute it and/or modify it under
* the terms of the GNU Affero General Public License as published by the Free
* Software Foundation; either version 3 of the License, or (at your option) any
* later version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
*
* You should have received a copy of the GNU Affero General Public License
* along with this program; if not, see http://www.gnu.org/licenses.
*
* Additional permission under GNU GPL version 3 section 7:
*
* If you modify this Program, or any covered work, by linking or combining it
* with Jersey (https://jersey.java.net) (or a modified version of that
* library), containing parts covered by the terms of the General Public
* License, version 2.0, the licensors of this Program grant you additional
* permission to convey the resulting work.
*/


import de.samply.share.common.utils.MdrIdDatatype;
import de.samply.share.client.quality.report.centraxx.CentraxxMapper;
import de.samply.share.client.quality.report.centraxx.CentraxxMapperException;
import de.samply.share.client.quality.report.centraxx.CentraxxMapperImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/centraxx-mapper-test")
public class CentraXxMapperTest {

    @GET
    public String myTest() throws CentraxxMapperException {

        CentraxxMapper centraXxMapper = new CentraxxMapperImpl();

        String centraXxAttribute = centraXxMapper.getCentraXxAttribute(new MdrIdDatatype("urn:dktk:dataelement:33:2"));
        String centraXxValue = centraXxMapper.getCentraXxValue(new MdrIdDatatype("urn:dktk:dataelement:77:1"), "nicht erfasst");


        return "CentraXX Mapper Test!";
    }

}
