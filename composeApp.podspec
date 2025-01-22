Pod::Spec.new do |spec|
  spec.name         = 'composeApp'
  spec.version      = '1.0.0'
  spec.summary      = 'A short description of composeApp.'
  spec.homepage     = 'https://skyfit.world'
  spec.license      = { :type => 'MIT', :file => 'LICENSE' }
  spec.author       = { 'Mert Vurgun' => 'vurgunmert@outlook.com' }
  spec.source       = { :path => '.' }

  spec.ios.deployment_target = '17.0'
  spec.source_files = 'Sources/**/*.{swift,h,m}'
  
  # If you have a dynamic framework:
#   spec.vendored_frameworks = 'Frameworks/composeApp.framework'
end
