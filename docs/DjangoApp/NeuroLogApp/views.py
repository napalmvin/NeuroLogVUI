# from django.shortcuts import render

# Create your views here.
from django.http import HttpResponse
from django.http.response import HttpResponseRedirect
from django.shortcuts import render

from NeuroLogApp.forms import DoctorsForm
from NeuroLogApp.forms import PatientsForm
from NeuroLogApp.forms import GeneralPhysicalExamsForm
from NeuroLogApp.forms import GenExamsAerialRecordsForm
from NeuroLogApp.forms import ClinicalExamsForm
from NeuroLogApp.forms import PainRecordsForm
from NeuroLogApp.forms import HeadacheRecordsForm
from NeuroLogApp.forms import NeurologicalExamsForm

from django.views.generic.edit import FormView
import logging

logger=logging.getLogger('views')


def index(request):
    return HttpResponse("Hello, world. You're at the NeuroLogApp index.")



class DoctorView(FormView):
    template_name = 'doctor_form.html'
    form_class = DoctorsForm
    # success_url = '/doctor_created/'
    success_url = '../doctor_created/'

    # def get(self, request, *args, **kwargs):
    #     logger.error('GET')
    #     return super(DoctorView,self).get(self,args,kwargs)
    #
    # def post(self, request, *args, **kwargs):
    #     logger.error('POOOOOOOOOST')
    #     return super(DoctorView,self).post(self, args, kwargs)

    def form_valid(self, form):
        logger.error('FORM IS VALID')
        form.save()
        return super(DoctorView,self).form_valid(self)

    # def form_invalid(self,form):
    #     logger.error('FORM IS NOT VALID')
    #     return super(DoctorView,self).form_invalid(self)

# def upload_file(request):
#     logger.error('upload_file')
#     if request.method == 'POST':
#         form = DoctorsForm(request.POST, request.FILES)
#         if form.is_valid():
#             # file is saved
#             form.save()
#             return HttpResponseRedirect('/success/url/')
#     else:
#         form = DoctorsForm()
#     return render(request, 'upload.html', {'form': form})

class PatientView(FormView):
    template_name = 'patient_form.html'
    form_class = PatientsForm  
    # success_url = '/thanks/' 


class GeneralPhysicalExamsView(FormView):
    template_name = 'gen_phys_exam_form.html'
    form_class = GeneralPhysicalExamsForm
    # success_url = '/thanks/'


class GenExamsAerialRecordsView(FormView):
    template_name = 'gen_exam_aer_form.html'
    form_class = GenExamsAerialRecordsForm  
    # success_url = '/thanks/'


class ClinicalExamsView(FormView):
    template_name = 'clinical_exam_form.html'
    form_class = ClinicalExamsForm  
    # success_url = '/thanks/'


class PainRecordsView(FormView):
    template_name = 'pain_record_form.html'
    form_class = PainRecordsForm  
    # success_url = '/thanks/'


class HeadacheRecordsView(FormView):
    template_name = 'headache_record_form.html'
    form_class = HeadacheRecordsForm  
    # success_url = '/thanks/'


class NeurologicalExamsView(FormView):
    template_name = 'neuro_ex__form.html'
    form_class = NeurologicalExamsForm  
    # success_url = '/thanks/'