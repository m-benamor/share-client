package de.samply.share.client.quality.report.file.excel.instances.patientids;/*
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

import java.util.*;

public class PatientIdsList implements Iterable<List<String>>{

    private List<List<String>> lists = new ArrayList<>();
    private List<String> maxList = new ArrayList<>();

    public void addList (Collection<String> myCollection){

        List<String> myList = new ArrayList<>(myCollection);
        Collections.sort(myList);

        lists.add(myList);

        if (myList.size() > maxList.size()){
            maxList = myList;
        }

    }

    private class PatientIdsIterator implements Iterator<List<String>>{

        private Iterator<String> myIterator;
        int index = 0;

        public PatientIdsIterator() {
            myIterator = maxList.iterator();
        }

        @Override
        public boolean hasNext() {
            return myIterator.hasNext();
        }

        @Override
        public List<String> next() {

            myIterator.next();
            List<String> myList = createList(index);
            index++;

            return myList;
        }

        private List<String> createList(int index){

            List<String> newList = new ArrayList<>();

            for (List<String> myList : lists){
                String element = (myList.size() > index) ? myList.get(index) : "";
                newList.add(element);
            }

            return newList;

        }

        @Override
        public void remove() {

        }

    }

    public Integer getMaxNumberOfPatientsOfAllPatientLists(){
        return maxList.size();
    }

    @Override
    public Iterator<List<String>> iterator() {
        return new PatientIdsIterator();
    }

}
