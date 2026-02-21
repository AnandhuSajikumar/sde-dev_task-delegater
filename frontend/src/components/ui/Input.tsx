import { InputHTMLAttributes, forwardRef } from 'react';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
    label?: string;
    error?: string;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(
    ({ label, error, className, ...props }, ref) => {
        return (
            <div className="flex flex-col gap-1 mb-3">
                {label && <label className="text-sm font-medium text-gray-700 mb-1 block" style={{ color: 'var(--color-text-secondary)' }}>{label}</label>}
                <input
                    ref={ref}
                    className={`input-field ${error ? 'border-red-500' : ''} ${className || ''}`}
                    {...props}
                />
                {error && <span className="text-xs text-red-500 mt-1" style={{ color: 'var(--color-status-danger)' }}>{error}</span>}
            </div>
        );
    }
);

Input.displayName = 'Input';
