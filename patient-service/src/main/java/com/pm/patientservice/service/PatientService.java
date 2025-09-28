package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repositoy.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::PatientResponseToDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientResponseDTO){

        Patient newPatient = patientRepository.save(
                PatientMapper.toPatientModel(patientResponseDTO)
        );
        return PatientMapper.PatientResponseToDTO(newPatient);
    }
}
